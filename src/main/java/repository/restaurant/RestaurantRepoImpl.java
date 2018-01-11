package repository.restaurant;

import model.Restaurant;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RestaurantRepoImpl implements RestaurantRepo
{
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Restaurant save(Restaurant restaurant)
    {
        if(restaurant.getId() == null)
        {
            em.persist(restaurant);
            return restaurant;
        }
        return em.merge(restaurant);
    }

    @Override
    public Restaurant get(int restaurantId)
    {
        return em.find(Restaurant.class, restaurantId);
    }

    @Override
    public Restaurant getByName(String name)
    {
       return DataAccessUtils.singleResult(em.createNamedQuery(Restaurant.GET_BY_NAME, Restaurant.class)
               .setParameter("restaurantName", name)
               .getResultList());
    }

    @Override
    public List<Restaurant> getAll()
    {
        return em.createNamedQuery(Restaurant.GET_ALL_REST, Restaurant.class)
                .getResultList();
    }

    @Transactional
    @Override
    public boolean deleteRestaurant(int restaurantId)
    {
        return em.createNamedQuery(Restaurant.DELETE_REST)
                .setParameter("id", restaurantId)
                .executeUpdate() != 0;
    }

    @Override
    public int getVotes(String name)
    {
        return getByName(name).getVotesCounter();
    }
}
