package tests;

import api.OrderApi;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.hasItems;

public class OrderListTest {

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что API возвращает список заказов, и он содержит хотя бы один заказ")
    public void testGetOrderList() {
        Response response = sendGetOrderListRequest();
        validateOrderListResponse(response);
    }

    @Step("Отправка запроса на получение списка заказов")
    private Response sendGetOrderListRequest() {
        return OrderApi.getOrderList();
    }

    @Step("Проверка успешного ответа на получение списка заказов")
    private void validateOrderListResponse(Response response) {
        response.then().assertThat()
                .statusCode(200)
                .body("orders", notNullValue())  // Проверяем, что orders не null
                .body("orders.size()", notNullValue())  // Проверяем, что в ответе есть массив orders
                .body("orders.id", hasItems());  // Проверяем, что в массиве orders есть хотя бы один заказ
    }
}
