package controller;

import model.Dish;
import model.Restaurant;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import repository.dish.DishRepo;
import repository.restaurant.RestaurantRepo;
import repository.user.UserRepo;
import util.PasswordUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static util.ExistenceUtil.*;

@RestController
@RequestMapping(AdminRestController.REST_URL)
public class AdminRestController
{
    static final String REST_URL = "/rest/admin";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishRepo dishRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @GetMapping(value = "/allusers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers()
    {
       log.info("get all users");
       return userRepo.getAll();
    }

    @PostMapping(value="/registernewuser", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createNewUser(@Valid @RequestBody User user)
    {
        checkIdAbsence(user);
        checkUserUniqueEmail(userRepo, user);
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        User createdUser = userRepo.save(user);
        log.info("new user with id=" + createdUser.getId() + " was created");

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "getuser?userId={userId}")
                .buildAndExpand(createdUser.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(createdUser);
    }

    @GetMapping(value = "/getuser", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@RequestParam("userId") int userId)
    {
        log.info("get user with id=" + userId);
        return userRepo.getUser(userId);
    }

    @DeleteMapping(value = "/deleteuser")
    public void deleteUser(@RequestParam("userId") int userId)
    {
        checkUserExistence(userRepo, userId);
        userRepo.deleteUser(userId);
        log.info("the user with id=" + userId + " was removed");
    }


    @PostMapping(value = "/newrestaurant", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createNewRestaurant(@Valid @RequestBody Restaurant restaurant)
    {
       checkIdAbsence(restaurant);
       Restaurant newRest = restaurantRepo.save(restaurant);
       log.info("restaurant {} with id={} was created", restaurant, restaurant.getId());
       URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
               .path(UserRestController.REST_URL + "/getrestaurant?restId=" + "{id}")
               .buildAndExpand(newRest.getId()).toUri();

       return ResponseEntity.created(uriOfNewResource).body(newRest);
    }

    @PutMapping(value = "/updaterestaurant", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateRestaurant(@Valid @RequestBody Restaurant restaurant, @RequestParam("restId") int restId)
    {
        checkIdCorrespondence(restaurant, restId);
        checkRestaurantExistence(restaurantRepo, restId);
        restaurantRepo.save(restaurant);
        log.info("update {} with id={} ", restaurant, restId);
    }

    @DeleteMapping(value = "/deleterestaurant")
    public void delete(@RequestParam("restId") int restId)
    {
        checkRestaurantExistence(restaurantRepo, restId);
        restaurantRepo.deleteRestaurant(restId);
        log.info("remove restaurant with id " + restId);
    }


    @PostMapping(value = "/newdish", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createNewDish(@Valid @RequestBody Dish dish, @RequestParam("restId") int restId)
    {
        checkIdAbsence(dish);
        checkRestaurantExistence(restaurantRepo, restId);
        Dish newDish = dishRepo.saveDish(dish, restId);
        log.info("new dish with name={} and price={} with id={} created for restaurant with id={} ", newDish.getDishName(), newDish.getPrice(), newDish.getId(), restId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(UserRestController.REST_URL + "/getdish?restId=" + "{restId}&dishId="+"{dishId}")
                .buildAndExpand(restId, newDish.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(newDish);
    }

    @PutMapping(value = "/updatedish", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateDish(@Valid @RequestBody Dish dish, @RequestParam("restId") int restId, @RequestParam("dishId") int dishId)
    {
        checkIdCorrespondence(dish, dishId);
        checkDishExistence(dishRepo, dishId, restId);
        dishRepo.saveDish(dish, restId);
        log.info("updating dish name={} and price={} with id={} for restaurant with restId={}", dish.getDishName(), dish.getPrice(), dishId, restId);
    }

    @DeleteMapping(value = "/deletedish")
    public void removeDish(@RequestParam("restId") int restId, @RequestParam("dishId") int dishId)
    {
        checkDishExistence(dishRepo, dishId, restId);
        dishRepo.deleteDish(dishId, restId);
        log.info("remove dish with id={} for restaurant with restId={}", dishId, restId);
    }

    @DeleteMapping(value = "/deletealldishes")
    public void removeAllDishes(@RequestParam("restId") int restId)
    {
        checkRestaurantExistence(restaurantRepo, restId);
        List<Dish> allDishes = dishRepo.getAllDishes(restId);
        for(Dish dish : allDishes)
        {
            removeDish(restId, dish.getId());
        }
    }

    @PostMapping(value = "/restartvote")
    public void restartVote()
    {
        for(Restaurant restaurant : restaurantRepo.getAll())
        {
            restaurant.setVotesCounter(0);
            restaurantRepo.save(restaurant);
            log.info("restaurant's with id=" + restaurant.getId()+" votes was set to 0");
        }

        for(User user : userRepo.getAll())
        {
            user.setVotescounter(0);
            user.setRestaurant(null);
            userRepo.save(user);
            log.info("user with id=" + user.getId() + " was refreshed");
        }
        log.info("voting for today's was revocated");
    }
}
