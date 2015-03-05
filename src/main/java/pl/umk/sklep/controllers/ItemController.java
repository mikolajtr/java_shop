package pl.umk.sklep.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.umk.sklep.utils.CookieConnection;
import pl.umk.sklep.utils.Item;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import static pl.umk.sklep.utils.DatabaseConnection.connectToDatabase;

@Controller("/addItem")
public class ItemController {

    @RequestMapping("/addItemForm")
    public String addNewItemFromPage(ModelMap model, HttpServletRequest request) throws SQLException, ClassNotFoundException {
        Cookie cookie = CookieConnection.getCookie(request);
        String username = CookieConnection.getUsernameFromCookie(cookie);

        if (username == null) {
            return "notlogged";
        }

        model.addAttribute("logged", username);

        return "addItemForm";
    }

    @RequestMapping("/add_item")
    public String addNewItem(ModelMap model, HttpServletRequest request) throws ClassNotFoundException, SQLException, IOException {

        Cookie cookie = CookieConnection.getCookie(request);
        String username = CookieConnection.getUsernameFromCookie(cookie);
        model.addAttribute("logged", username);

        String name;
        String owner = username;
        String description;
        String want;
        String image;
        String contact;

        name = request.getParameter("name");
        description = request.getParameter("description");
        want = request.getParameter("want");
        image = request.getParameter("image");
        contact = request.getParameter("contact");

        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO items VALUES (" + null + ", \'" + name + "\', \'" + owner + "\', \'" + description + "\', \'" + want + "\', \'" + image + "\', \'" + contact + "\', DATE(\'now\'));");
        connection.close();

        return "succesItemAdding";
    }

    @RequestMapping("/itemList")
    public String itemList(ModelMap model, HttpServletRequest request) throws ClassNotFoundException, SQLException {

        Cookie cookie = CookieConnection.getCookie(request);
        String username = CookieConnection.getUsernameFromCookie(cookie);
        model.addAttribute("logged", username);

        List<String> itemList = new LinkedList<String>();
        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();

        statement.executeUpdate("DELETE FROM items WHERE julianday(\'now\') - julianday(date) > 3;");

        ResultSet resultSet = statement.executeQuery("SELECT * FROM items;");

        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String owner = resultSet.getString("owner");
            String description = resultSet.getString("description");
            String want = resultSet.getString("want");
            String image = resultSet.getString("image");
            String contact = resultSet.getString("contact");

            Item item_temp = new Item(name, owner, description, want, image, contact, id);
            itemList.add(item_temp.shortItemRepresentation());
        }

        model.addAttribute("items", itemList);

        return "itemlist";
    }

    @RequestMapping("/search")
    public String searchItem(ModelMap model, HttpServletRequest request) throws ClassNotFoundException, SQLException {

        Cookie cookie = CookieConnection.getCookie(request);
        String username = CookieConnection.getUsernameFromCookie(cookie);
        model.addAttribute("logged", username);

        List<String> itemList = new LinkedList<String>();
        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();

        String value = request.getParameter("value");

        ResultSet resultSet = statement.executeQuery("" +
                "SELECT * FROM items " +
                "WHERE name like \'%" + value + "%\' or description  like \'%" + value + "%\';");

        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String owner = resultSet.getString("owner");
            String description = resultSet.getString("description");
            String want = resultSet.getString("want");
            String image = resultSet.getString("image");
            String contact = resultSet.getString("contact");

            Item item_temp = new Item(name, owner, description, want, image, contact, id);
            itemList.add(item_temp.shortItemRepresentation());
        }

        model.addAttribute("items", itemList);

        return "itemlist";
    }

    @RequestMapping(value = "/viewItem/{item_id}", method = RequestMethod.GET)
    public String viewItem(@PathVariable Integer item_id, ModelMap model, HttpServletRequest request) throws SQLException, ClassNotFoundException {

        Cookie cookie = CookieConnection.getCookie(request);
        String username = CookieConnection.getUsernameFromCookie(cookie);
        model.addAttribute("logged", username);

        Connection connection = connectToDatabase();
        Statement statement = connection.createStatement();
        ResultSet resultSet = null;
        Item item;

        resultSet = statement.executeQuery("SELECT * FROM items WHERE id = " + item_id + ";");
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String owner = resultSet.getString("owner");
        String description = resultSet.getString("description");
        String want = resultSet.getString("want");
        String image = resultSet.getString("image");
        String contact = resultSet.getString("contact");

        item = new Item(name, owner, description, want, image, contact, id);
        model.addAttribute("item", item.itemRepresentation());

        return "viewitem";
    }
}
