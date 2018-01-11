package auth;

import model.User;


public class AuthorizedUser extends org.springframework.security.core.userdetails.User
{
    private static final long serialVersionUID = 1L;

    private User user;

    public AuthorizedUser(User user)
    {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.user = user;
    }

    public int getId()
    {
        return user.getId();
    }


    public User getUser()
    {
        return user;
    }

    @Override
    public String toString()
    {
        return user.toString();
    }
}