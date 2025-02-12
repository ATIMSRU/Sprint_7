package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;
import models.CancelOrderRequest;

import static io.restassured.RestAssured.given;

public class OrderApi {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDER_ENDPOINT = "/api/v1/orders";
    private static final String CANCEL_ORDER_ENDPOINT = "/api/v1/orders/cancel";

    @Step("Создание заказа")
    public static Response createOrder(Order order) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(order) // сериализация объекта Order
                .when()
                .post(ORDER_ENDPOINT);
    }

    @Step("Отмена заказа")
    public static Response cancelOrder(int track) {
        CancelOrderRequest cancelRequest = new CancelOrderRequest(track); // объект для сериализации
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .body(cancelRequest) // объект, который будет сериализован в JSON
                .when()
                .put(CANCEL_ORDER_ENDPOINT);
    }

    @Step("Получение списка заказов")
    public static Response getOrderList() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-Type", "application/json")
                .when()
                .get(ORDER_ENDPOINT);
    }
}
