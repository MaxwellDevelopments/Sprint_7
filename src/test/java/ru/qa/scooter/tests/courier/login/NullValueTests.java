package ru.qa.scooter.tests.courier.login;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.qa.scooter.business.pojo.courier.Courier;
import ru.qa.scooter.tests.base.BaseScooter;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.utils.api.requests.CourierApi;

import static org.hamcrest.Matchers.is;

@Epic("Courier login. Negative tests with missed parameters")
class NullValueTests extends BaseScooter {

    @Test
    @DisplayName("POST /api/v1/courier/login (no login)")
    @Description("Try to login without necessary parameter in JSON (login). Code 400")
    void cantLoginWithoutLogin() {
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String jsonPath = "message";
        String expected = "Недостаточно данных для входа";

        Response responseLogin = CourierApi.loginCourier(new Courier<>(null, password, null));

        Checkers.check400BadRequest(responseLogin);
        Checkers.checkAnswerInResponse(responseLogin, jsonPath, is(expected));
    }

    @Test
    @DisplayName("POST /api/v1/courier/login (no password)")
    @Description("Try to login without necessary parameter in JSON (password). Code 400")
    void cantLoginWithoutPassword() {
        String login = Constants.FAKER.name().username();
        String jsonPath = "message";
        String expected = "Недостаточно данных для входа";

        Response responseLogin = CourierApi.loginCourier(new Courier<>(login, null, null));

        Checkers.check400BadRequest(responseLogin);
        Checkers.checkAnswerInResponse(responseLogin, jsonPath, is(expected));
    }
}
