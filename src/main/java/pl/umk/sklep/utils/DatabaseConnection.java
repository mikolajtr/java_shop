package pl.umk.sklep.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection connectToDatabase() throws ClassNotFoundException {
        Connection connection = null;

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:base.sqlite");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
