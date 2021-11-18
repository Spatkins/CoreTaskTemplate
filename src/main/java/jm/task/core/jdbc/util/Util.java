package jm.task.core.jdbc.util;

import java.util.Properties;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String SERV_CONNECTION = "jdbc:mysql://localhost:3306/users";
    private static final String USER_LOGIN = "root";
    private static final String USER_PASSWORD = "QP1Abd9ZGtocfeBLic6V";
    private static SessionFactory factory;

    public static SessionFactory getSession() {
        if (factory == null) {
            Configuration config = new Configuration();
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", SERV_CONNECTION);
            prop.setProperty("hibernate.connection.username", USER_LOGIN);
            prop.setProperty("hibernate.connection.password", USER_PASSWORD);
            prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
            prop.setProperty("hibernate.show_sql", "true");
            prop.setProperty("hibernate.current_session_context_class", "thread");
            prop.setProperty("hibernate.hbm2ddl.auto", "create-drop");
            config.setProperties(prop);
            config.addAnnotatedClass(User.class);
            ServiceRegistry service = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties())
                    .build();
            factory = config.buildSessionFactory(service);
        }
        return factory;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(SERV_CONNECTION, USER_LOGIN, USER_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
