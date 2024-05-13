package ru.qa.scooter.tests.courier.create;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.qa.scooter.tests.base.BaseScooter;
import ru.qa.scooter.utils.api.requests.Postcondition;
import ru.qa.scooter.business.pojo.courier.Courier;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.requests.CourierApi;
import ru.qa.scooter.utils.global.Constants;

@Epic("Courier create. Negative tests with non-string types of parameters in JSON")
class NegativeBadTypeTests extends BaseScooter {

    private enum CourierCreated {
        LOGIN,
        PASSWORD,
        FIRSTNAME,
        NOTHING
    }

    private static Courier<Number, String, String> courierNumLogin;
    private static Courier<String, Number, String> courierNumPassword;
    private static Courier<String, String, Number> courierNumFirstname;
    private static CourierCreated courierCreated;

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad login type in JSON)")
    @Description("Negative test for creating a courier with integer login value, status code 400")
    @ValueSource(ints = {1234, 0, 1, -1, -2, 2, 1259158})
    void cantCreateCourierIntegerLogin(Integer login) {
        String errorMessage = String.format(
                        "Courier was created with integer login \"%d\" value",
                        login
        );

        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String firstName = Constants.FAKER.name().firstName();

        courierNumLogin = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumLogin);
        checkResponse400IfElseThrowErrorIf201DelCourier(responseCreate, errorMessage, CourierCreated.LOGIN);

    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad password type)")
    @Description("Negative test for creating a courier with integer password, status code 400")
    @ValueSource(ints = {1234, 0, 1, -1, -2, 2, 1259158})
    void cantCreateCourierIntegerPassword(Integer password) {
        String errorMessage = String.format(
                "Courier was created with integer password \"%d\" value",
                password
        );

        String login = Constants.FAKER.name().username();
        String firstName = Constants.FAKER.name().firstName();


        courierNumPassword = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumPassword);
        checkResponse400IfElseThrowErrorIf201DelCourier(responseCreate, errorMessage, CourierCreated.PASSWORD);

    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad firstName type)")
    @Description("Negative test for creating a courier with integer firstName, status code 400")
    @ValueSource(ints = {1234, 0, 1, -1, -2, 2, 1259158})
    void cantCreateCourierIntegerFirstName(Integer firstName) {
        String errorMessage = String.format(
                "Courier was created with integer firstName \"%d\" value",
                firstName
        );

        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);

        courierNumFirstname = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumFirstname);
        checkResponse400IfElseThrowErrorIf201DelCourier(responseCreate, errorMessage, CourierCreated.FIRSTNAME);

    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad login type)")
    @Description("Negative test for creating a courier with float login, status code 400")
    @ValueSource(floats = {1234.0f, 0.0f, 1.0f, 0.1f, -0.1f, 2.0f, 1259158f})
    void cantCreateCourierFloatLogin(Float login) {
        String errorMessage = String.format(
                "Courier was created with float login \"%f\" value",
                login
        );

        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String firstName = Constants.FAKER.name().firstName();

        courierNumLogin = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumLogin);
        checkResponse400IfElseThrowErrorIf201DelCourier(responseCreate, errorMessage, CourierCreated.LOGIN);

    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad password type)")
    @Description("Negative test for creating a courier with float password, status code 400")
    @ValueSource(floats = {1234.0f, 0.0f, 1.0f, 0.1f, -0.1f, 2.0f, 1259158f})
    void cantCreateCourierFloatPassword(Float password) {
        String errorMessage = String.format(
                "Courier was created with float password \"%f\" value",
                password
        );

        String login = Constants.FAKER.name().username();
        String firstName = Constants.FAKER.name().firstName();

        courierNumPassword = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumPassword);
        checkResponse400IfElseThrowErrorIf201DelCourier(responseCreate, errorMessage, CourierCreated.PASSWORD);

    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad firstName type)")
    @Description("Negative test for creating a courier with float firstName, status code 400")
    @ValueSource(floats = {1234.0f, 0.0f, 1.0f, 0.1f, -0.1f, 2.0f, 1259158f})
    void cantCreateCourierFloatFirstName(Float firstName) {
        String errorMessage = String.format(
                "Courier was created with float firstName \"%f\" value",
                firstName
        );

        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);

        courierNumFirstname = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumFirstname);
        checkResponse400IfElseThrowErrorIf201DelCourier(responseCreate, errorMessage, CourierCreated.FIRSTNAME);

    }

    private void checkResponse400IfElseThrowErrorIf201DelCourier(
            Response responseCreate,
            String errorMessage,
            CourierCreated courierToDelete
    ) {
        try {
            courierCreated = CourierCreated.NOTHING;
            Checkers.check400BadRequest(responseCreate);            
        }
        catch (AssertionError e) {
            if (responseCreate.getStatusCode() == 201) {
                courierCreated = courierToDelete;
                throw new AssertionError(errorMessage);
            }
            throw e;
        }
    }

    @AfterEach
    public void deleteCourier() {
        switch (courierCreated) {
            case LOGIN:
                Postcondition.Courier.delete(courierNumLogin);
                break;
            case PASSWORD:
                Postcondition.Courier.delete(courierNumPassword);
                break;
            case FIRSTNAME:
                Postcondition.Courier.delete(courierNumFirstname);
                break;
            case NOTHING:
        }
    }

}
