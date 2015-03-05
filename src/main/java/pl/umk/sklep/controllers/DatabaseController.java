package pl.umk.sklep.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static pl.umk.sklep.utils.DatabaseConnection.connectToDatabase;

@Controller
public class DatabaseController extends HttpServlet {

    @RequestMapping("/register")
    public String registerUserFromForm() {
        return "register";
    }

    @RequestMapping("/add_user")
    public String registerNewUser(ModelMap model, HttpServletRequest request) throws ClassNotFoundException, SQLException {
        String username, password, passwordAgain;
        username = request.getParameter("username");
        password = request.getParameter("password");
        passwordAgain = request.getParameter("passwordAgain");

        if (!password.equals(passwordAgain)) {
            model.addAttribute("message", "Hasła się nie zgadzają. Podaj poprawne hasło.");
            return "register";
        }

        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select * from users;");
        while (resultSet.next()) {
            String username_tmp = null;
            username_tmp = resultSet.getString("username");

            if (username_tmp.equals(username)) {
                connection.close();
                model.addAttribute("message", "Użytkownik o takiej nazwie już istnieje. Wybierz inną nazwę użytkownika.");
                return "register";
            }
        }
            statement.executeUpdate("INSERT INTO users VALUES (\'" + username + "\', '" + password + "\');");
            connection.close();

            return "succes";
    }
}