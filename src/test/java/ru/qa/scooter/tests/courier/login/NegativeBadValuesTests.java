package ru.qa.scooter.tests.courier.login;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.qa.scooter.business.pojo.courier.Courier;
import ru.qa.scooter.tests.base.BaseScooter;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.requests.Postcondition;
import ru.qa.scooter.utils.api.requests.Precondition;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.utils.api.requests.CourierApi;

@Epic("Courier login. Negative tests with wrong values of parameters.")
class NegativeBadValuesTests extends BaseScooter {

    @Test
    @DisplayName("POST /api/v1/courier/login with bad password")
    @Description("Negative test for login courier with wrong password")
    void cantLoginBadPassword() {
        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String firstName = Constants.FAKER.name().firstName();

        Courier<String, String, String> courier = new Courier<>(login, password, firstName);

        Precondition.Courier.create(courier);

        Response responseLogin = CourierApi.loginCourier(courier);

        try {
            Checkers.check404NotFound(responseLogin);
        } catch (AssertionError e) {
            if (responseLogin.getStatusCode() == 201) {
                courier.setPassword(password);
                Postcondition.Courier.delete(courier);
            }
            throw e;
        }


    }

    @Test
    @DisplayName("POST /api/v1/courier/login. Negative. Courier doesn't exist")
    @Description("Negative test for login courier with non-existed login, status code 404")
    void cantLoginCourierDoesNotExist() {
        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);

        String jsonPath = "message";
        String expected = "Учетная запись не найдена";

        Courier<String, String, String> courier = new Courier<>(login, password, null);

        Response responseLogin = CourierApi.loginCourier(courier);

        Checkers.check404NotFound(responseLogin);
        Checkers.checkAnswerInResponse(responseLogin, jsonPath, expected);
    }
}
