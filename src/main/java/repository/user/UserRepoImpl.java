package repository.user;

import auth.AuthorizedUser;
import model.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("userRepo")
@Transactional(readOnly = true)
public class UserRepoImpl implements UserRepo, UserDetailsService
{
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public User save(User user)
    {
        if(user.getId() == null)
        {
            em.persist(user);
            return user;
        }
        return em.merge(user);
    }

    @Override
    public User getByEmail(String email)
    {
        return DataAccessUtils.singleResult(em.createNamedQuery(User.GET_BY_EMAIL, User.class)
                .setParameter("email", email.toLowerCase())
                .getResultList());
    }

    @Override
    public User getUser(int userId)
    {
        return em.find(User.class, userId);
    }

    @Override
    public User getWithRestaurant(int userId)
    {
        return DataAccessUtils.singleResult(em.createNamedQuery(User.GET_WITH_RESTAURANT, User.class)
                .setParameter("userId", userId)
                .getResultList());
    }

    @Override
    public List<User> getAll()
    {
        return em.createNamedQuery(User.GET_ALL_SORTED, User.class)
                .getResultList();
    }

    @Transactional
    @Override
    public boolean deleteUser(int userId)
    {
        return em.createNamedQuery(User.DELETE_USER)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User user = getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

   /* @Override
    public boolean voteForRest(int userId, int restaurantId)
    {
        return false;
    }

    @Override
    public void setNewMenu(Restaurant restaurant, List<Dish> dishes)
    {

    }*/
}
