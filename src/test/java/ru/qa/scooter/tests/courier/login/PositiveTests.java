package ru.qa.scooter.tests.courier.login;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.qa.scooter.business.pojo.courier.Courier;
import ru.qa.scooter.business.pojo.courier.CourierId;
import ru.qa.scooter.tests.base.BaseScooter;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.requests.Postcondition;
import ru.qa.scooter.utils.api.requests.Precondition;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.utils.api.requests.CourierApi;


@Epic("Courier login. Positive tests")
class PositiveTests extends BaseScooter {

    public static Courier<String, String, String> courier;

    @BeforeAll
    public static void createCourier() {
        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String firstName = Constants.FAKER.name().firstName();

        courier = new Courier<>(login, password, firstName);

        Precondition.Courier.create(courier);
    }

    static int courierId;

    @Test
    @DisplayName("Test POST /api/v1/courier/login positive")
    @Description("Positive test: login courier with login and password. Status code 200")
    void courierCanLoginPositiveTest() {
        String jsonPath = "id";

        Response responseLogin = CourierApi.loginCourier(courier);

        Checkers.check200Success(responseLogin);
        Checkers.checkAnswerInResponseIsNotNull(responseLogin, jsonPath);

        courierId = responseLogin.body().as(CourierId.class).getId();
    }

    @AfterAll
    public static void delCourier() {
        Postcondition.Courier.delete(courierId);
    }
}
