package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final UserDao userDao = new UserDaoJDBCImpl();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Util.getSession().beginTransaction();
        Util.getSession().save(new User(name, lastName, age));
        Util.getSession().getTransaction().commit();
        System.out.println("User successfully add!" + name);
    }

    @Override
    public void removeUserById(long id) {
        Util.getSession().beginTransaction();
        Util.getSession().delete(Util.getSession().get(User.class, id));
        Util.getSession().getTransaction().commit();
        System.out.println("User successfully deleted!" + id);
    }

    @Override
    public List<User> getAllUsers() {
        Util.getSession().beginTransaction();
        List<User> usersList = Util.getSession().createQuery("from User").getResultList();
        Util.getSession().getTransaction().commit();
        System.out.println(usersList);
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        Util.getSession().beginTransaction();
        Util.getSession().createQuery("delete User").executeUpdate();
        Util.getSession().getTransaction().commit();
        System.out.println("Table successfully cleaned!");
    }
}
