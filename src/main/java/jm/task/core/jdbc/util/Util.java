package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String SERV_CONNECTION =
            "jdbc:mysql://localhost:3306/users?useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String USER_LOGIN = "root";
    private static final String USER_PASSWORD = "QP1Abd9ZGtocfeBLic6V";
    private Connection connection = null;

    public Connection getConnection() {

        try {
            connection = DriverManager.getConnection(SERV_CONNECTION, USER_LOGIN, USER_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
