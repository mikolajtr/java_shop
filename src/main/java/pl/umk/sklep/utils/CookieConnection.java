package pl.umk.sklep.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static pl.umk.sklep.utils.DatabaseConnection.connectToDatabase;

public class CookieConnection {

    public static Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        Cookie cookie = null;

        for (Cookie c : cookies) {
            if (c.getName().equals("rupieciarnia")) {
                cookie = c;
                break;
            }
        }

        return cookie;
    }

    public static String getUsernameFromCookie(Cookie cookie) throws SQLException, ClassNotFoundException {

        String session_id;
        String username;
        ResultSet resultSet = null;

        try {
            session_id = cookie.getValue();
            System.out.println("Id sesji: " + session_id);
        } catch (NullPointerException e) {
            return null;
        }

        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();


        try {
            resultSet = statement.executeQuery("select * from sessions where session_id=\'" + session_id + "\';");
        } catch (SQLException e) {
            System.out.println("resultSet = " + resultSet);
        }

        try {
            username = resultSet.getString("username");
        } catch (NullPointerException e) {
            username = null;
        }
        System.out.println("username=" + username);

        connection.close();

        return username;
    }
}
