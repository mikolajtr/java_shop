package pl.umk.sklep.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.umk.sklep.utils.CookieConnection;
import pl.umk.sklep.utils.MD5Hash;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static pl.umk.sklep.utils.DatabaseConnection.connectToDatabase;

@Controller
public class MainController {

    @RequestMapping("/login")
    public String loginUserForm() {
        return "login";
    }

    @RequestMapping(value = "/login_user", method = RequestMethod.POST)
    public String loginUserFromDatabase(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {

        String username;
        String password;

        username = request.getParameter("username");
        password = request.getParameter("password");

        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from users;");

        while (resultSet.next()) {
            String username_tmp, password_tmp;
            username_tmp = resultSet.getString("username");
            password_tmp = resultSet.getString("password");
            System.out.println(username + "=" + username_tmp + " " +password+ "="+password_tmp);

            if (username_tmp.equals(username)) {
                if (password_tmp.equals(password)){
                    String session_id = MD5Hash.getMD5Hash(username + "" + LocalDateTime.now());
                    System.out.println("Id sesji: " + session_id);

                    try {
                        statement.executeUpdate("delete from sessions where username=\'"+ username +"\';"); //kasujemy dane o poprzedniej sesji usera
                    } catch (SQLException e) {
                        System.out.println("Przedtem nie było sesji takiego usera.");
                    }

                    try {
                        statement.executeUpdate("INSERT INTO sessions VALUES (\'" + username + "\', '" + session_id + "\');"); //wstawiamy id sesji, jeśli już takie jest, to user już jest zalogowany
                    } catch (SQLException e) {
                        model.addAttribute("message", "Już jesteś zalogowany.");
                        System.out.println("Ten user już się zalogował.");
                    } finally {
                        model.addAttribute("logged", username);
                    }

                    Cookie cookie = new Cookie("rupieciarnia", session_id);
                    response.addCookie(cookie);
                    return "index";
                }

                connection.close();
                model.addAttribute("message", "Nieprawidłowe hasło.");
                return "login";
            }
        }

        connection.close();
        model.addAttribute("message", "Nieprawidłowa nazwa użytkownika.");
        return "login";
    }

    @RequestMapping("/logout")
    public String logoutUserFromDatabase(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException {

        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();

        Cookie cookie = CookieConnection.getCookie(request);
        String username = CookieConnection.getUsernameFromCookie(cookie);

        try {
            statement.executeUpdate("delete from sessions where username="+ username +";"); //kasujemy dane o poprzedniej sesji usera
        } catch (SQLException e) {
            System.out.println("Przedtem nie było sesji takiego usera.");
        }

        cookie = new Cookie("rupieciarnia", "");
        cookie.setMaxAge(1);
        response.addCookie(cookie);
        return "index";
    }
}
