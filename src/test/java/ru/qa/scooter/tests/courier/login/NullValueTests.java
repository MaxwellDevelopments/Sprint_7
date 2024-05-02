package ru.qa.scooter.tests.courier.login;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.qa.scooter.business.pojo.courier.CourierOnlyLogin;
import ru.qa.scooter.business.pojo.courier.CourierOnlyPassword;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.utils.api.requests.CourierApi;
import ru.qa.scooter.utils.Shuffler;
import static org.hamcrest.Matchers.is;

@Epic("Courier login. Negative tests with missed parameters")
class NullValueTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = Constants.LINK;
    }

    @Test
    @DisplayName("POST /api/v1/courier/login (no login)")
    @Description("Try to login without necessary parameter in JSON (login). Code 400")
    void cantLoginWithoutLogin() {
        String password = Shuffler.shuffle("agsjkgs");
        String jsonPath = "message";
        String expected = "Недостаточно данных для входа";

        Response responseLogin = CourierApi.loginCourier(new CourierOnlyPassword<>(password));

        Checkers.check400BadRequest(responseLogin);
        Checkers.checkAnswerInResponse(responseLogin, jsonPath, is(expected));
    }

    @Test
    @DisplayName("POST /api/v1/courier/login (no password)")
    @Description("Try to login without necessary parameter in JSON (password). Code 400")
    void cantLoginWithoutPassword() {
        String login = Shuffler.shuffle("agsjkgs");
        String jsonPath = "message";
        String expected = "Недостаточно данных для входа";

        Response responseLogin = CourierApi.loginCourier(new CourierOnlyLogin<>(login));

        Checkers.check400BadRequest(responseLogin);
        Checkers.checkAnswerInResponse(responseLogin, jsonPath, is(expected));
    }
}
