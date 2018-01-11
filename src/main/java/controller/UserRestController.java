package controller;

import auth.AuthorizedUser;
import model.Dish;
import model.Restaurant;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import repository.dish.DishRepo;
import repository.restaurant.RestaurantRepo;
import repository.user.UserRepo;
import util.PasswordUtil;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static util.ExistenceUtil.*;

@RestController
@RequestMapping(UserRestController.REST_URL)
public class UserRestController
{
    static final String REST_URL = "/rest";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private DishRepo dishRepo;

    @Autowired
    private UserRepo userRepo;


    @GetMapping(value = "/allrestaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAllRestaurants()
    {
        log.info("get all restaurants");
        return restaurantRepo.getAll();
    }

    @GetMapping(value = "/getrestaurant", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant getRestaurant(@RequestParam("restId") int restId)
    {
        checkRestaurantExistence(restaurantRepo, restId);
        log.info("getting restaurant with id=" + restId);
        return restaurantRepo.get(restId);
    }


    @GetMapping(value = "/alldishes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAllDishes(@RequestParam("restId") int restId)
    {
        checkRestaurantExistence(restaurantRepo, restId);
        log.info("get all dishes for the restaurant with id " + restId);
        return dishRepo.getAllDishes(restId);
    }

    @GetMapping(value = "/getdish", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish getDish(@RequestParam("restId") int restId, @RequestParam("dishId") int dishId)
    {
        checkDishExistence(dishRepo, dishId, restId);
        log.info("get a dish with dishId={} for rest with restId={}", dishId,restId);
        return dishRepo.getDish(dishId, restId);
    }


    @PostMapping(value = "/voteforrest")
    public void tryVoteForRest(@AuthenticationPrincipal AuthorizedUser authorizedUser, @RequestParam("restId") int restId)
    {
        User authUser = userRepo.getUser(authorizedUser.getId());
        checkRestaurantExistence(restaurantRepo, restId);
        Restaurant restToVote = restaurantRepo.get(restId);
        if(authUser.getVotescounter() == 0)
        {
            voteForRest(userRepo, authUser, restaurantRepo, restToVote);
            log.info("got the vote from the user with id=" + authUser.getId() + " for the restaurant with id=" + restId);
        }
        else if(authUser.getVotescounter() < 2 && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 12)
        {
            revoteForRest(userRepo, authUser, restaurantRepo, restToVote);
            log.info("user with id=" + authUser.getId() + " has changed his mind to restaurant with id=" + restId);
        }
        else throw new UnsupportedOperationException("The user with id=" + authUser.getId() + " is not allowed to vote today.");
    }

    @PutMapping(value = "/updateuser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@Valid @RequestBody User user, @AuthenticationPrincipal AuthorizedUser authorizedUser)
    {
        User updatedUser = userRepo.getUser(authorizedUser.getId());
        checkUserUniqueEmail(userRepo, user, updatedUser);
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(PasswordUtil.encode(user.getPassword()));
        updatedUser.setVotescounter(authorizedUser.getUser().getVotescounter());
        updatedUser.setRoles(authorizedUser.getUser().getRoles());
        userRepo.save(updatedUser);
    }

    public static void voteForRest(UserRepo userRepo, User user, RestaurantRepo restaurantRepo, Restaurant restaurant)
    {
        user.setRestaurant(restaurant);
        restaurant.setVotesCounter(restaurant.getVotesCounter() + 1);
        user.setVotescounter(user.getVotescounter() + 1);
        restaurantRepo.save(restaurant);
        userRepo.save(user);
    }

    public static void revoteForRest(UserRepo userRepo, User user, RestaurantRepo restaurantRepo, Restaurant newRestaurant)
    {
        Restaurant restaurantBefore = userRepo.getWithRestaurant(user.getId()).getRestaurant();
        checkIfRestsAreDifferent(restaurantBefore.getId(), newRestaurant.getId());
        restaurantBefore.setVotesCounter(restaurantBefore.getVotesCounter() - 1);
        newRestaurant.setVotesCounter(newRestaurant.getVotesCounter() + 1);
        user.setRestaurant(newRestaurant);
        user.setVotescounter(user.getVotescounter() + 1);
        restaurantRepo.save(restaurantBefore);
        restaurantRepo.save(newRestaurant);
        userRepo.save(user);
    }
}
