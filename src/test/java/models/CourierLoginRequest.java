package models;

// Класс-модель для авторизации курьера.
public class CourierLoginRequest {
    private String login;
    private String password;

    //Конструктор для создания объекта запроса авторизации.
    public CourierLoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    // Геттеры и сеттеры для сериализации/десериализации JSON
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
