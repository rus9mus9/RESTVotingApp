package repository.dish;

import model.Dish;
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
public class DishRepoImpl implements DishRepo
{
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Dish saveDish(Dish dish, int restaurantId)
    {
        if(dish.getId() != null && getDish(dish.getId(), restaurantId) == null)
        {
            return null;
        }
        dish.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        if(dish.getId() == null)
        {
            em.persist(dish);
            return dish;
        }
        else
        return em.merge(dish);
    }

    @Transactional
    @Override
    public boolean deleteDish(int dishId, int restaurantId)
    {
        return em.createNamedQuery(Dish.DELETE_DISH)
                .setParameter("dishId", dishId)
                .setParameter("restaurantId", restaurantId)
                .executeUpdate() != 0;
    }

    @Override
    public Dish getDish(int dishId, int restaurantId)
    {
        Dish dish = em.find(Dish.class, dishId);
        return dish != null && dish.getRestaurant().getId() == restaurantId ? dish : null;
    }

    @Override
    public Dish getDishByName(String dishName, int restaurantId)
    {
      return DataAccessUtils.singleResult(em.createNamedQuery(Dish.GET_DISH_BY_NAME, Dish.class)
              .setParameter("dishName", dishName)
              .setParameter("restId", restaurantId)
              .getResultList());
    }

    @Override
    public List<Dish> getAllDishes(int restaurantId)
    {
        return em.createNamedQuery(Dish.GET_ALL_DISHES_REST, Dish.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }

}
