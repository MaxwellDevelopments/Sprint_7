package ru.qa.scooter.tests.courier.create;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.restassured.response.Response;
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

    private static Courier<Number, String, String> courierNumLogin;
    private static Courier<String, Number, String> courierNumPassword;
    private static Courier<String, String, Number> courierNumFirstname;

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad login type in JSON)")
    @Description("Negative test for creating a courier with integer login value, status code 400")
    @ValueSource(ints = {1234, 0, 1, -1, -2, 2, 1259158})
    void cantCreateCourierIntegerParameter(Integer login) {

        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String firstName = Constants.FAKER.name().firstName();

        courierNumLogin = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumLogin);
        checkResponse400If201ThrowError(responseCreate, courierNumLogin);

    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad password type)")
    @Description("Negative test for creating a courier with integer password, status code 400")
    @ValueSource(ints = {1234, 0, 1, -1, -2, 2, 1259158})
    void cantCreateCourierIntegerPassword(Integer password) {
        String login = Constants.FAKER.name().username();
        String firstName = Constants.FAKER.name().firstName();

        courierNumPassword = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumPassword);
        checkResponse400If201ThrowError(responseCreate, courierNumPassword);


    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad firstName type)")
    @Description("Negative test for creating a courier with integer firstName, status code 400")
    @ValueSource(ints = {1234, 0, 1, -1, -2, 2, 1259158})
    void cantCreateCourierIntegerFirstName(Integer firstName) {
        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);

        courierNumFirstname = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumFirstname);
        checkResponse400If201ThrowError(responseCreate, courierNumFirstname);

    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad login type)")
    @Description("Negative test for creating a courier with float login, status code 400")
    @ValueSource(floats = {1234.0f, 0.0f, 1.0f, 0.1f, -0.1f, 2.0f, 1259158f})
    void cantCreateCourierFloatLogin(Float login) {
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String firstName = Constants.FAKER.name().firstName();

        courierNumLogin = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumLogin);
        checkResponse400If201ThrowError(responseCreate, courierNumLogin);

    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad password type)")
    @Description("Negative test for creating a courier with float password, status code 400")
    @ValueSource(floats = {1234.0f, 0.0f, 1.0f, 0.1f, -0.1f, 2.0f, 1259158f})
    void cantCreateCourierFloatPassword(Float password) {
        String login = Constants.FAKER.name().username();
        String firstName = Constants.FAKER.name().firstName();

        courierNumPassword = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumPassword);
        checkResponse400If201ThrowError(responseCreate, courierNumPassword);

    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad firstName type)")
    @Description("Negative test for creating a courier with float firstName, status code 400")
    @ValueSource(floats = {1234.0f, 0.0f, 1.0f, 0.1f, -0.1f, 2.0f, 1259158f})
    void cantCreateCourierFloatFirstName(Float firstName) {
        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);

        courierNumFirstname = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courierNumFirstname);
        checkResponse400If201ThrowError(responseCreate, courierNumFirstname);

    }

    @Step("Check that status in response is 400. If status code is 201, then delete courier.")
    private <T, V, K> void checkResponse400If201ThrowError(Response responseCreate, Courier<T, V, K> courier) {
        try {
            Checkers.check400BadRequest(responseCreate);
        }
        catch (AssertionError e) {
            Checkers.check201Created(responseCreate);
            Postcondition.Courier.delete(courier);
            throw e;
        }
    }

}
