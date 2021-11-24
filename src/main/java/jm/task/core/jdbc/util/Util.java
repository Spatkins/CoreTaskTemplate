package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String SERV_CONNECTION = "jdbc:mysql://localhost:3306/users";
    private static final String USER_LOGIN = "root";
    private static final String USER_PASSWORD = "QP1Abd9ZGtocfeBLic6V";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(SERV_CONNECTION, USER_LOGIN, USER_PASSWORD);
    }
}
