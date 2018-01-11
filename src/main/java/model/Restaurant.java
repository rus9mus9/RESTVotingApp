package model;

import org.hibernate.validator.constraints.Range;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@NamedQueries(
        {
                @NamedQuery(name = Restaurant.GET_BY_NAME, query = "SELECT r FROM Restaurant r WHERE r.restaurantName=:restaurantName"),
                @NamedQuery(name = Restaurant.GET_ALL_REST, query = "SELECT r FROM Restaurant r ORDER BY r.votesCounter DESC"),
                @NamedQuery(name = Restaurant.DELETE_REST, query = "DELETE FROM Restaurant r WHERE r.id=:id"),
        })
@Table(name = "restaurants")
@Entity
public class Restaurant extends AbstractEntity
{
    public static final String GET_BY_NAME = "Restaurant.getByName";
    public static final String GET_ALL_REST = "Restaurant.getAll";
    public static final String DELETE_REST = "Restaurant.delete";

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    private String restaurantName;

    @Column(name = "votescounter", nullable = false, columnDefinition = "int default 0")
    @Range(min = 0)
    private int votesCounter;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<User> votedUsers;

    public Restaurant()
    {

    }

    public List<User> getVotedUsers()
    {
        return votedUsers;
    }

    public void setVotedUsers(List<User> votedUsers)
    {
        this.votedUsers = votedUsers;
    }

    public Restaurant(Integer id, String restaurantName, int votesCounter)
    {
        super(id);
        this.restaurantName = restaurantName;
        this.votesCounter = votesCounter;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getVotesCounter() {
        return votesCounter;
    }

    public void setVotesCounter(int votesCounter) {
        this.votesCounter = votesCounter;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
