package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import models.Courier;

public class CourierApi {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Step("Создание курьера")
    public static Response createCourier(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Авторизация курьера")
    public static Response loginCourier(String login, String password) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body("{\"login\":\"" + login + "\", \"password\":\"" + password + "\"}")
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера")
    public static Response deleteCourier(int courierId) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
}
