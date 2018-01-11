package model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NamedQueries(
        {
                @NamedQuery(name = Dish.DELETE_DISH, query = "DELETE FROM Dish d WHERE d.id=:dishId AND d.restaurant.id=:restaurantId"),
                @NamedQuery(name = Dish.GET_ALL_DISHES_REST, query = "SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId ORDER BY d.id"),
                @NamedQuery(name = Dish.GET_DISH_BY_NAME, query = "SELECT d FROM Dish d WHERE d.dishName=:dishName AND d.restaurant.id=:restId")
        })
@Table(name = "dishes")
@Entity
public class Dish extends AbstractEntity
{
    public static final String DELETE_DISH = "Dish.delete";
    public static final String GET_ALL_DISHES_REST = "Dish.getAllDishes";
    public static final String GET_DISH_BY_NAME = "Dish.getByName";

    @Column(name = "name", nullable = false)
    @NotBlank
    private String dishName;

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish()
    {

    }

    public Dish(int id, String dishName, Integer price)
    {
        super(id);
        this.dishName = dishName;
        this.price = price;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
