package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            Util.getSession().beginTransaction();
            Util.getSession().createSQLQuery("CREATE TABLE if not exists users_table  " +
                            "(id int auto_increment, " +
                            " name VARCHAR(16) null , " +
                            " lastname VARCHAR (16) null , " +
                            " age int null , " +
                            " PRIMARY KEY (id))")
                    .addEntity(User.class);
            Util.getSession().getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            try {
                if (Util.getSession() != null) {
                    Util.getSession().getTransaction().rollback();
                }
            } catch (HibernateException ex) {
                ex.printStackTrace();
            } finally {
                Util.getSession().close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Util.getSession().beginTransaction();
            Util.getSession().createSQLQuery("DROP TABLE if exists users_table  ")
                    .addEntity(User.class);
            Util.getSession().getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            try {
                if (Util.getSession() != null) {
                    Util.getSession().getTransaction().rollback();
                }
            } catch (HibernateException ex) {
                ex.printStackTrace();
            } finally {
                Util.getSession().close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Util.getSession().beginTransaction();
            Util.getSession().save(new User(name, lastName, age));
            Util.getSession().getTransaction().commit();
            System.out.println("User successfully add!" + name);
        } catch (HibernateException e) {
            if (Util.getSession() != null) {
                Util.getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            Util.getSession().close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Util.getSession().beginTransaction();
            Util.getSession().delete(Util.getSession().get(User.class, id));
            Util.getSession().getTransaction().commit();
            System.out.println("User successfully deleted!" + id);
        } catch (HibernateException e) {
            if (Util.getSession() != null) {
                Util.getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            Util.getSession().close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = null;
        try {
            Util.getSession().beginTransaction();
            usersList = Util.getSession().createQuery("from User").getResultList();
            Util.getSession().getTransaction().commit();
            System.out.println(usersList);
            return usersList;
        } catch (HibernateException e) {
            if (Util.getSession() != null) {
                Util.getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            Util.getSession().close();
        }
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Util.getSession().beginTransaction();
            Util.getSession().createQuery("delete User").executeUpdate();
            Util.getSession().getTransaction().commit();
            System.out.println("Table successfully cleaned!");
        } catch (HibernateException e) {
            if (Util.getSession() != null) {
                Util.getSession().getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            Util.getSession().close();
        }
    }
}
