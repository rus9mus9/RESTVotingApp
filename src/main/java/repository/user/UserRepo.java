package repository.user;

import model.Dish;
import model.Restaurant;
import model.User;

import java.util.List;

public interface UserRepo
{
    User save(User user);
    User getByEmail(String email);
    User getUser(int userId);
    User getWithRestaurant(int userId);
    List<User> getAll();
    boolean deleteUser(int userId);

    //Restaurant getRestaurant(int userId, int restId)

    /*boolean voteForRest(int userId, int restaurantId);
    void setNewMenu(Restaurant restaurant, List<Dish> dishes);*/
}
