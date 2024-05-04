package ru.qa.scooter.tests.courier.create;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.qa.scooter.business.pojo.courier.*;
import ru.qa.scooter.tests.base.BaseScooter;
import ru.qa.scooter.utils.api.requests.Postcondition;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.requests.CourierApi;

@Epic("Courier create. Negative tests with null or empty values of login/password/firstname JSON params")
class NullValueTests extends BaseScooter {

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (null value)")
    @Description("Negative test for creating a courier with null values in JSON (login/password/firstName)," +
            " status code 400")
    @CsvSource({
            "null, pass, firstName",
            "gahsjg, null, firstName",
            "gahsj, pass, null"
    })
    void cantCreateCourierNullValue(String login, String password, String firstName) {
        if (login.equals("null")) {
            login = null;
        }
        else if (password.equals("null")) {
            password = null;
        }
        else if (firstName.equals("null")) {
            firstName = null;
        }

        String errorMessage = String.format(
                "User was created with null value of parameter %s%s%s",
                login == null ? "login" : "",
                password == null ? "password" : "",
                firstName == null ? "firstName" : ""
        );

        Courier<String, String, String> courier = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courier);
        checkResponse400If201DeleteAndThrowError(responseCreate, courier, errorMessage);
    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (empty value)")
    @Description("Negative test for creating a courier with empty values in JSON (login/password/firstName)," +
            " status code 400")
    @CsvSource({
            "empty, pass, firstName",
            "gahsjg, empty, firstName",
            "gahsj, pass, empty"
    })
    void cantCreateCourierEmptyValue(String login, String password, String firstName) {
        String errorMessage = String.format(
                "User was created with %s%s%s empty value",
                login.equals("empty") ? "login" : "",
                password.equals("empty") ? "password" : "",
                firstName.equals("empty") ? "firstName" : ""
        );

        if (login.equals("empty")) {
            login = "";
        }
        else if (password.equals("empty")) {
            password = "";
        }
        else if (firstName.equals("empty")) {
            firstName = "";
        }

        Courier<String, String, String> courier = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courier);
        checkResponse400If201DeleteAndThrowError(responseCreate, courier, errorMessage);
    }

    @Step("Check that status in response is 400. If status code is 201, then delete courier.")
    private <T, V, K> void checkResponse400If201DeleteAndThrowError(
            Response responseCreate,
            Courier<T, V, K> courier,
            String errorMessage) {

        try {
            Checkers.check400BadRequest(responseCreate);
        }
        catch (AssertionError e) {
            Checkers.check201Created(responseCreate);
            Postcondition.Courier.delete(courier);
            throw new AssertionError(errorMessage);
        }
    }


}
