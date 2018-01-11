package repository.dish;

import model.Dish;

import java.util.List;

public interface DishRepo
{
    Dish saveDish(Dish dish, int restaurantId);
    Dish getDish(int dishId, int restaurantId);
    Dish getDishByName(String dishName, int restaurantId);
    List<Dish> getAllDishes(int restaurantId);
    boolean deleteDish(int dishId, int restaurantId);
}
