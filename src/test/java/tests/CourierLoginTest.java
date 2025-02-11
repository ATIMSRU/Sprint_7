package tests;

import api.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CourierLoginTest {
    private Courier courier;
    private int courierId;

    @Before
    @Step("Создание тестового курьера перед тестом")
    public void setUp() {
        courier = Courier.getRandomCourier();
        Response response = CourierApi.createCourier(courier);
        response.then().assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка, что курьер может авторизоваться и получает ID")
    public void testCourierCanLogin() {
        Response response = CourierApi.loginCourier(courier.getLogin(), courier.getPassword());
        response.then().assertThat().statusCode(200).body("id", notNullValue());
        courierId = response.jsonPath().getInt("id");
    }

    @Test
    @DisplayName("Ошибка при авторизации без логина")
    @Description("Проверка, что система не позволяет авторизоваться без логина")
    public void testLoginWithoutLogin() {
        Response response = CourierApi.loginCourier("", courier.getPassword());
        response.then().assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка при авторизации без пароля")
    @Description("Проверка, что система не позволяет авторизоваться без пароля")
    public void testLoginWithoutPassword() {
        Response response = CourierApi.loginCourier(courier.getLogin(), "");
        response.then().assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка при неверном логине")
    @Description("Проверка, что система не позволяет авторизоваться с неверным логином")
    public void testLoginWithIncorrectLogin() {
        Response response = CourierApi.loginCourier("wrongLogin", courier.getPassword());
        response.then().assertThat().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка при неверном пароле")
    @Description("Проверка, что система не позволяет авторизоваться с неверным паролем")
    public void testLoginWithIncorrectPassword() {
        Response response = CourierApi.loginCourier(courier.getLogin(), "wrongPassword");
        response.then().assertThat().statusCode(404).body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    @Step("Удаление тестового курьера после теста")
    public void tearDown() {
        if (courierId != 0) {
            CourierApi.deleteCourier(courierId);
        }
    }
}
