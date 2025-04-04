package web.dao;

import org.springframework.stereotype.Component;
import web.models.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    private static int USERS_COUNT;
    private List<User> users;

    {
        users = new ArrayList<>();
        users.add(new User(++USERS_COUNT, "Harry Potter"));
        users.add(new User(++USERS_COUNT, "Hermione Granger"));
        users.add(new User(++USERS_COUNT, "Ron Weasley"));
        users.add(new User(++USERS_COUNT, "Albus Dumbledore"));
        users.add(new User(++USERS_COUNT, "Severus Snape"));
        users.add(new User(++USERS_COUNT, "Luna Lovegood"));
        users.add(new User(++USERS_COUNT, "Draco Malfoy"));
        users.add(new User(++USERS_COUNT, "Rubeus Hagrid"));
    }

    public List<User> index() {
        return users;
    }

    public User show(int id) {
        return users.stream().filter(user -> user.getId() == id).findAny().orElse(null);
    }

    public void save(User user) {
        user.setId(++USERS_COUNT);
        users.add(user);
    }

}
