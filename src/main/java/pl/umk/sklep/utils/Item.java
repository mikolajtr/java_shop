package pl.umk.sklep.utils;

public class Item {

    public Integer id;
    public String name;
    public String owner;
    public String description;
    public String want;
    public String image;
    public String contact;

    public Item(String name, String owner, String description, String want, String image, String contact, Integer id) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.want = want;
        this.image = image;
        this.contact = contact;
        this.id = id;
    }

    public String itemRepresentation() {

        String item = "<img src=\"" + image + "\" width=\"250\" height=\"250\" align=\"left\">" +
                "Nazwa: " + name + "</br>" +
                "Właściciel: " + owner + "</br>" +
                "Opis: " + description + "</br>" +
                "Chce: " + want + "</br>" +
                "Kontakt: " + contact + "</br>";

        return item;
    }

    public String shortItemRepresentation() {

        String item = "<img src=\"" + image + "\" width=\"250\" height=\"250\" align=\"left\">" +
                "Nazwa: " + name + "</br>" +
                "Właściciel: " + owner + "</br>" +
                "<a href=\"/viewItem/" + id +"\">Zobacz opis</a></br>";

        return item;
    }
}
