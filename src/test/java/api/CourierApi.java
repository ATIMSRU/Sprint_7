package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import models.Courier;
import models.CourierLoginRequest;

public class CourierApi {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_ENDPOINT = "/api/v1/courier";
    private static final String COURIER_LOGIN_ENDPOINT = "/api/v1/courier/login";

    @Step("Создание курьера")
    public static Response createCourier(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(courier) //  сериализация вместо строкового представления JSON
                .when()
                .post(COURIER_ENDPOINT);
    }

    @Step("Авторизация курьера")
    public static Response loginCourier(String login, String password) {
        CourierLoginRequest loginRequest = new CourierLoginRequest(login, password); // объект с логином и паролем, который будет сериализован в JSON
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(loginRequest) // объект вместо строки
                .when()
                .post(COURIER_LOGIN_ENDPOINT);
    }

    @Step("Удаление курьера")
    public static Response deleteCourier(int courierId) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .when()
                .delete(COURIER_ENDPOINT + "/" + courierId);
    }
}
