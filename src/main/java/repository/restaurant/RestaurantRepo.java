package repository.restaurant;

import model.Dish;
import model.Restaurant;

import java.util.List;

public interface RestaurantRepo
{
    Restaurant save(Restaurant restaurant);
    Restaurant get(int restaurantId);
    Restaurant getByName(String name);
    List<Restaurant> getAll();
    boolean deleteRestaurant(int restaurantId);
    int getVotes(String name);
}
