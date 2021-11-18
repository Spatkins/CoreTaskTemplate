package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Statement statement;
    private PreparedStatement insert;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            statement = Util.getConnection().createStatement();
            statement.executeUpdate( "CREATE TABLE if not exists users_table  " +
                    "(id int auto_increment, " +
                    " name VARCHAR(16) null , " +
                    " lastname VARCHAR (16) null , " +
                    " age int null , " +
                    " PRIMARY KEY (id))");
            System.out.println("Table successfully created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            statement = Util.getConnection().createStatement();
            statement.executeUpdate( "DROP TABLE if exists users_table  ");
            System.out.println("Table successfully dropped!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            Util.getConnection().setAutoCommit(false);
            insert = Util.getConnection().prepareStatement ("insert into users_table(name, lastname, age) "
                    + " VALUES (?, ?, ?)");
            insert.setString(1, name);
            insert.setString(2, lastName);
            insert.setByte(3, age);
            insert.execute();
            Util.getConnection().commit();
            System.out.println("User successfully add!" + name);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try {
            Util.getConnection().setAutoCommit(false);
            insert = Util.getConnection().prepareStatement ("delete from users_table where id = ?");
            insert.setLong(1, id);
            insert.execute();
            Util.getConnection().commit();
            System.out.println("User successfully deleted!" + id);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try {
            Util.getConnection().setAutoCommit(false);
            ResultSet resultSet = Util.getConnection().createStatement()
                    .executeQuery("select * from users_table ");
            while (resultSet.next()) {
                usersList.add(new User(resultSet.getLong("id"),
                                       resultSet.getString("name"),
                                       resultSet.getString("lastname"),
                                       resultSet.getByte("age")));
            }
            Util.getConnection().commit();
            System.out.println(usersList);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                Util.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try {
            statement = Util.getConnection().createStatement();
            statement.executeUpdate( "truncate table users_table");
            System.out.println("Table successfully cleaned!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
