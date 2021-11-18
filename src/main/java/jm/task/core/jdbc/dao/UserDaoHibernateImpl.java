package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final UserDao userDao = new UserDaoJDBCImpl();
    private Session session;

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
        session = Util.getSession().getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        System.out.println("User successfully add!" + name);
    }

    @Override
    public void removeUserById(long id) {
        session = Util.getSession().getCurrentSession();
        session.beginTransaction();
        session.delete(session.get(User.class, id));
        session.getTransaction().commit();
        System.out.println("User successfully deleted!" + id);
    }

    @Override
    public List<User> getAllUsers() {
        session = Util.getSession().getCurrentSession();
        session.beginTransaction();
        List<User> usersList = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        System.out.println(usersList);
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        session = Util.getSession().getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
        System.out.println("Table successfully cleaned!");
    }
}
