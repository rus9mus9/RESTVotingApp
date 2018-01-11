package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.CollectionUtils;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@NamedQueries(
        {
           @NamedQuery(name = User.GET_BY_EMAIL, query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=:email"),
           @NamedQuery(name = User.GET_ALL_SORTED, query = "SELECT u FROM User u ORDER BY u.id"),
           @NamedQuery(name = User.DELETE_USER, query = "DELETE FROM User u WHERE u.id=:userId"),
           @NamedQuery(name = User.GET_WITH_RESTAURANT, query = "SELECT u FROM User u JOIN FETCH u.restaurant WHERE u.id=:userId" )
        })
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User extends AbstractEntity
{
    public static final String GET_BY_EMAIL = "User.getByEmail";
    public static final String GET_ALL_SORTED = "User.getAll";
    public static final String DELETE_USER = "User.delete";
    public static final String GET_WITH_RESTAURANT = "User.getRest";

    public int getVotescounter()
    {
        return votescounter;
    }

    public void setVotescounter(int votescounter)
    {
        this.votescounter = votescounter;
    }

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 64)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "votescounter", nullable = false)
    @Range(min = 0)
    private int votescounter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Restaurant getRestaurant()
    {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant)
    {
        this.restaurant = restaurant;
    }

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;


    public User()
    {

    }

    public User(Integer id, String email, String password, int votescounter, Collection<Role> roles)
    {
        super(id);
        this.email = email;
        this.password = password;
        this.votescounter = votescounter;
        setRoles(roles);
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

}
