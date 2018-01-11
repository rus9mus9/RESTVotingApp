package util;

import model.AbstractEntity;
import model.User;
import repository.dish.DishRepo;
import repository.restaurant.RestaurantRepo;
import repository.user.UserRepo;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

public class ExistenceUtil
{
    private ExistenceUtil()
    {

    }

    public static void checkIdCorrespondence(AbstractEntity entity, int id)
    {
        if(entity.getId() != id)
        {
            throw new IllegalArgumentException(entity + " should be with id=" + id);
        }
    }

    public static void checkIdAbsence(AbstractEntity entity)
    {
        if(entity.getId() != null)
        {
            throw new IllegalArgumentException("New entity shouldn't have ID!");
        }
    }

    public static void checkRestaurantExistence(RestaurantRepo restaurantRepo, int restId)
    {
        if(restaurantRepo.get(restId) == null)
        {
            throw new NoResultException("Restaurant with id=" + restId + " is not found.");
        }
    }

    public static void checkDishExistence(DishRepo dishRepo, int dishId, int restId)
    {
        if(dishRepo.getDish(dishId, restId) == null)
        {
            throw new NoResultException("Dish with id=" + dishId + " is not found in restaurant with id=" + restId);
        }
    }

    public static void checkUserExistence(UserRepo userRepo, int userId)
    {
        if(userRepo.getUser(userId) == null)
        {
            throw new NoResultException("User with id=" + userId + " is not found!");
        }
    }

    public static void checkUserUniqueEmail(UserRepo userRepo, User user)
    {
        if(userRepo.getByEmail(user.getEmail()) != null)
        {
            throw new EntityExistsException("User with email " + user.getEmail() + " already exists!");
        }
    }

    public static void checkUserUniqueEmail(UserRepo userRepo, User user, User anotherUser)
    {
        User userFromDB = userRepo.getByEmail(user.getEmail());

        if(userFromDB != null)
        {
            if(!userFromDB.getEmail().equals(anotherUser.getEmail()))
            {
                throw new EntityExistsException("User with email " + user.getEmail() + " already exists!");
            }

        }
    }

    public static void checkIfRestsAreDifferent(int restId1, int restId2)
    {
        if(restId1 == restId2)
        {
            throw new UnsupportedOperationException("You can't vote for the same restaurant twice");
        }
    }

}
