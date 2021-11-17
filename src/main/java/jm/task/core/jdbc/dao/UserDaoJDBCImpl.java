package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util util = new Util();
    private Statement statement;
    private PreparedStatement insert;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            statement = util.getConnection().createStatement();
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
            statement = util.getConnection().createStatement();
            statement.executeUpdate( "DROP TABLE if exists users_table  ");

            System.out.println("Table successfully dropped!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            util.getConnection().setAutoCommit(false);
            insert = util.getConnection().prepareStatement ("insert into users_table(name, lastname, age) "
                    + " VALUES (?, ?, ?)");
            insert.setString(1, name);
            insert.setString(2, lastName);
            insert.setByte(3, age);
            insert.execute();
            util.getConnection().commit();
            System.out.println("User successfully add!" + name);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                util.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
    }

    public void removeUserById(long id) {
        try {
            util.getConnection().setAutoCommit(false);
            insert = util.getConnection().prepareStatement ("delete from users_table where id = ?");
            insert.setLong(1, id);
            insert.execute();
            util.getConnection().commit();
            System.out.println("User successfully deleted!" + id);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                util.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {

        List<User> usersList = new ArrayList<>();
        try {
            ResultSet resultSet = util.getConnection().createStatement().executeQuery("select * from users_table ");
            while (resultSet.next()) {
                User newUser = new User();
                newUser.setId(resultSet.getLong("id"));
                newUser.setName(resultSet.getString("name"));
                newUser.setLastName(resultSet.getString("lastname"));
                newUser.setAge(resultSet.getByte("age"));
                usersList.add(newUser);
            }
            System.out.println(usersList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try {
            statement = util.getConnection().createStatement();
            statement.executeUpdate( "truncate table users_table");
            System.out.println("Table successfully cleaned!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
