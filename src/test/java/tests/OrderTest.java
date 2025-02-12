package tests;

import api.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTest {
    private int trackNumber;
    private final List<String> color;

    public OrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Collection<Object[]> getTestData() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("BLACK")},         // Один цвет - BLACK
                {Arrays.asList("GREY")},          // Один цвет - GREY
                {Arrays.asList("BLACK", "GREY")}, // Два цвета - BLACK и GREY
                {null}                            // Без указания цвета
        });
    }

    @Before
    @Step("Инициализация перед тестом")
    public void setUp() {
        trackNumber = 0; // Сбрасываем значение перед каждым тестом
    }

    @Test
    @DisplayName("Создание заказа с разными вариантами цвета")
    @Description("Проверяем, что можно создать заказ с одним цветом, двумя цветами или без указания цвета. Проверяем, что в ответе есть track.")
    public void testCreateOrder() {
        Order order = createOrderObject();
        Response response = sendCreateOrderRequest(order);
        validateOrderResponse(response);
        saveTrackNumber(response);
    }

    @Step("Создание объекта заказа с цветом: {color}")
    private Order createOrderObject() {
        return new Order(
                "Naruto", "Uchiha", "Konoha, 142 apt.", 4,
                "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", color
        );
    }

    @Step("Отправка запроса на создание заказа")
    private Response sendCreateOrderRequest(Order order) {
        return OrderApi.createOrder(order);
    }

    @Step("Проверка успешного ответа на создание заказа")
    private void validateOrderResponse(Response response) {
        response.then().assertThat().statusCode(201).body("track", notNullValue());
    }

    @Step("Сохранение track-номера заказа")
    private void saveTrackNumber(Response response) {
        trackNumber = response.jsonPath().getInt("track");
    }

    @After
    @Step("Удаление созданного заказа после теста")
    public void tearDown() {
        if (trackNumber != 0) {
            cancelOrder(trackNumber);
        }
    }

    @Step("Отправка запроса на удаление заказа с track: {trackNumber}")
    private void cancelOrder(int trackNumber) {
        OrderApi.cancelOrder(trackNumber);
    }
}
