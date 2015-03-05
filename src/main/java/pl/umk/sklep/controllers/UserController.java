package pl.umk.sklep.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.umk.sklep.utils.CookieConnection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static pl.umk.sklep.utils.DatabaseConnection.connectToDatabase;

@Controller
public class UserController {

    @RequestMapping("/userPanel")
    public String userPanel(ModelMap model, HttpServletRequest request) throws SQLException, ClassNotFoundException {

        Cookie cookie = CookieConnection.getCookie(request);
        String username = CookieConnection.getUsernameFromCookie(cookie);

        if (username == null) {
            return "notlogged";
        }

        model.addAttribute("logged", username);

        return "userpanel";
    }

    @RequestMapping(value = "/change_password", method = RequestMethod.POST)
    public String changePassword(ModelMap model, HttpServletRequest request) throws SQLException, ClassNotFoundException {

        Cookie cookie = CookieConnection.getCookie(request);
        String username = CookieConnection.getUsernameFromCookie(cookie);

        if (username == null) {
            return "notlogged";
        }

        model.addAttribute("logged", username);

        String oldPassword = request.getParameter("oldPassword");
        String oldPasswordAgain = request.getParameter("oldPasswordAgain");
        String newPassword = request.getParameter("newPassword");
        String newPasswordAgain = request.getParameter("newPasswordAgain");

        if (oldPassword.equals(oldPasswordAgain)) {
            if (newPassword.equals(newPasswordAgain)) {
                Connection connection = connectToDatabase();
                Statement statement = connection.createStatement();

                statement.executeUpdate("UPDATE users SET password = \'" + newPassword + "\' WHERE username = \'" + username + "\';");
                connection.close();
                model.addAttribute("message", "Twoje hasło zostało zmienione.");
                return "userpanel";
            } else {
                model.addAttribute("message", "Stare hasła się nie zgadzają. Sprawdź poprawność haseł.");
                return "userpanel";
            }
        } else {
            model.addAttribute("message", "Stare hasła się nie zgadzają. Sprawdź poprawność haseł.");
            return "userpanel";
        }
    }
}
