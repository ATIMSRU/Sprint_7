package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;

import static io.restassured.RestAssured.given;

public class OrderApi {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Step("Создание заказа")
    public static Response createOrder(Order order) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Отмена заказа")
    public static Response cancelOrder(int track) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body("{\"track\": " + track + "}")
                .when()
                .put("/api/v1/orders/cancel");
    }
    @Step("Получение списка заказов")
    public static Response getOrderList() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .when()
                .get("/api/v1/orders");
    }
}
