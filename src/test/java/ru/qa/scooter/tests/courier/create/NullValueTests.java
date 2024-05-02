package ru.qa.scooter.tests.courier.create;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.qa.scooter.utils.Shuffler;
import ru.qa.scooter.utils.api.requests.Postcondition;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.business.pojo.courier.Courier;
import ru.qa.scooter.business.pojo.courier.CourierWithoutFirstName;
import ru.qa.scooter.business.pojo.courier.CourierWithoutLogin;
import ru.qa.scooter.business.pojo.courier.CourierWithoutPassword;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.requests.CourierApi;

import static org.hamcrest.Matchers.notNullValue;

@Epic("Courier create. Negative tests with null and empty values of parameters in JSON")
class NullValueTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = Constants.LINK;
    }


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
        String errorMessage = String.format(
                "User was created with %s%s%s null value",
                login.equals("null") ? "login" : "",
                password.equals("null") ? "password" : "",
                firstName.equals("null") ? "firstName" : ""
        );
        if (login.equals("null")) {
            login = null;
        }
        else if (password.equals("null")) {
            password = null;
        }
        else if (firstName.equals("null")) {
            firstName = null;
        }

        Courier<String, String, String> courier = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courier);
        try {
            Checkers.check400BadRequest(responseCreate);
        }
        catch (AssertionError e) {
            MatcherAssert.assertThat(responseCreate, notNullValue());
            try {
                Checkers.check201Created(responseCreate);
            } catch (AssertionError e1) {
                throw e;
            }
            Postcondition.Courier.delete(
                    new CourierWithoutFirstName<>(courier.getLogin(), courier.getPassword())
            );
            throw new AssertionError(errorMessage);
        }
    }



    @Test
    @DisplayName("POST /api/v1/courier doesn't create a courier (missed parameter in JSON)")
    @Description("Negative test for creating a courier without login, status code 400")
    void cantCreateCourierWithoutLogin() {
        String password = Shuffler.shuffle("agsj");
        String firstName = Shuffler.shuffle("fjas");
        String errorMessage = "User was created without login";
        String responseMessage = "Недостаточно данных для создания учетной записи";

        CourierWithoutLogin<String, String> courier = new CourierWithoutLogin<>(password, firstName);

        Response responseCreate = CourierApi.createCourier(courier);
        try {
            Checkers.check400BadRequest(responseCreate);
            Checkers.checkAnswerInResponse(responseCreate, "message", responseMessage);
        }
        catch (AssertionError e) {
            try {
                Checkers.check201Created(responseCreate);
            } catch (AssertionError e1) {
                throw e;
            }
            Postcondition.Courier.delete(courier);
            throw new AssertionError(errorMessage);
        }
    }

    @Test
    @DisplayName("POST /api/v1/courier doesn't create a courier (missed parameter in JSON)")
    @Description("Negative test for creating a courier without password, status code 400")
    void cantCreateCourierWithoutPassword() {
        String login = "gakjs";
        String firstName = "fjas";
        String errorMessage = "User was created without password";
        String responseMessage = "Недостаточно данных для создания учетной записи";

        CourierWithoutPassword<String, String> courier = new CourierWithoutPassword<>(login, firstName);

        Response responseCreate = CourierApi.createCourier(courier);
        try {
            Checkers.check400BadRequest(responseCreate);
            Checkers.checkAnswerInResponse(responseCreate, "message", responseMessage);
        }
        catch (AssertionError e) {
            try {
                Checkers.check201Created(responseCreate);
                Checkers.checkAnswerInResponse(responseCreate, "message", responseMessage);
            } catch (AssertionError e1) {
                throw e;
            }
            Postcondition.Courier.delete(courier);
            throw new AssertionError(errorMessage);
        }
    }

    @Test
    @DisplayName("POST /api/v1/courier doesn't create a courier (missed parameter in JSON)")
    @Description("Negative test for creating a courier without firstName, status code 400")
    void cantCreateCourierWithoutFirstName() {
        String login = "bmzkl";
        String password = "agsj";
        String errorMessage = "User was created without firstName";
        String responseMessage = "Недостаточно данных для создания учетной записи";

        CourierWithoutFirstName<String, String> courier = new CourierWithoutFirstName<>(login, password);

        Response responseCreate = CourierApi.createCourier(courier);
        try {
            Checkers.check400BadRequest(responseCreate);
            Checkers.checkAnswerInResponse(responseCreate, "message", responseMessage);
        }
        catch (AssertionError e) {
            try {
                Checkers.check201Created(responseCreate);
            } catch (AssertionError e1) {
                throw e;
            }
            Postcondition.Courier.delete(courier);
            throw new AssertionError(errorMessage);
        }
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
        try {
            Checkers.check400BadRequest(responseCreate);
        }
        catch (AssertionError e) {
            MatcherAssert.assertThat(responseCreate, notNullValue());
            try {
                Checkers.check201Created(responseCreate);
            } catch (AssertionError e1) {
                throw e;
            }
            Postcondition.Courier.delete(
                    new CourierWithoutFirstName<>(courier.getLogin(), courier.getPassword())
            );
            throw new AssertionError(errorMessage);
        }
    }


}
