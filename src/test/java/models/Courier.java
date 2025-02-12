package models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // геттеры, сеттеры, toString(), equals() и hashCode()
@AllArgsConstructor // конструктор со всеми аргументами
public class Courier {
    private String login;
    private String password;
    private String firstName;

    public static Courier getRandomCourier() {
        return new Courier("testUser" + System.currentTimeMillis(), "password123", "Test");
    }
}
