package tests;

import api.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CourierTest {
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courier = Courier.getRandomCourier();
        Response response = CourierApi.createCourier(courier);
        response.then().assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка, что можно создать курьера")
    public void testCreateCourier() {
        Response response = CourierApi.createCourier(courier);
        response.then().assertThat().statusCode(409);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться")
    public void testCourierLogin() {
        Response response = CourierApi.loginCourier(courier.getLogin(), courier.getPassword());
        response.then().assertThat().statusCode(200).body("id", notNullValue());
        courierId = response.jsonPath().getInt("id");
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверка, что нельзя создать курьера без пароля")
    public void testCreateCourierWithoutPassword() {
        Courier invalidCourier = new Courier(courier.getLogin(), "", courier.getFirstName());
        Response response = CourierApi.createCourier(invalidCourier);
        response.then().assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            CourierApi.deleteCourier(courierId);
        }
    }
}
