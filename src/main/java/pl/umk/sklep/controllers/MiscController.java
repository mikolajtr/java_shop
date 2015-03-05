package pl.umk.sklep.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.umk.sklep.utils.CookieConnection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
public class MiscController {

    @RequestMapping("/")
    public String mainPage(ModelMap model, HttpServletRequest request) throws SQLException, ClassNotFoundException {

        Cookie cookie = CookieConnection.getCookie(request);
        String username = CookieConnection.getUsernameFromCookie(cookie);
        model.addAttribute("logged", username);

        return "index";
    }
}
