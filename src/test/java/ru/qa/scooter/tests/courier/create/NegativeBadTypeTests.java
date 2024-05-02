package ru.qa.scooter.tests.courier.create;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.qa.scooter.utils.Shuffler;
import ru.qa.scooter.utils.api.requests.Postcondition;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.business.pojo.courier.Courier;
import ru.qa.scooter.business.pojo.courier.CourierWithoutFirstName;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.requests.CourierApi;

import static org.hamcrest.Matchers.notNullValue;

@Epic("Courier create. Negative tests with non-string types of parameters in JSON")
class NegativeBadTypeTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = Constants.LINK;
    }

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad login type in JSON)")
    @Description("Negative test for creating a courier with integer login value, status code 400")
    @ValueSource(ints = {1234, 0, 1, -1, -2, 2, 1259158})
    void cantCreateCourierIntegerLogin(Integer login) {
        String password = Shuffler.shuffle("1234");
        String firstName = Shuffler.shuffle("firstName");
        String errorMessage = String.format(
                "User was created with integer %d login value",
                login
        );

        Courier<Integer, String, String> courier = new Courier<>(login, password, firstName);

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

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad password type)")
    @Description("Negative test for creating a courier with integer password, status code 400")
    @ValueSource(ints = {1234, 0, 1, -1, -2, 2, 1259158})
    void cantCreateCourierIntegerPassword(Integer password) {
        String login = Shuffler.shuffle("ytueytu");
        String firstName = Shuffler.shuffle("firstName");
        String errorMessage = String.format(
                "User was created with integer %d password value",
                password
        );


        Courier<String, Integer, String> courier = new Courier<>(login, password, firstName);

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

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad firstName type)")
    @Description("Negative test for creating a courier with integer firstName, status code 400")
    @ValueSource(ints = {1234, 0, 1, -1, -2, 2, 1259158})
    void cantCreateCourierIntegerFirstName(Integer firstName) {
        String login = Shuffler.shuffle("twoiuqi");
        String password = Shuffler.shuffle("ololoev");
        String errorMessage = String.format(
                "User was created with integer %d firstName value",
                firstName
        );

        Courier<String, String, Integer> courier = new Courier<>(login, password, firstName);

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


    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad login type)")
    @Description("Negative test for creating a courier with float login, status code 400")
    @ValueSource(floats = {1234.0f, 0.0f, 1.0f, 0.1f, -0.1f, 2.0f, 1259158f})
    void cantCreateCourierFloatLogin(Float login) {
        String password = Shuffler.shuffle("1234");
        String firstName = Shuffler.shuffle("firstName");
        String errorMessage = String.format(
                "User was created with float %f login value",
                login
        );

        Courier<Float, String, String> courier = new Courier<>(login, password, firstName);

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

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad password type)")
    @Description("Negative test for creating a courier with float password, status code 400")
    @ValueSource(floats = {1234.0f, 0.0f, 1.0f, 0.1f, -0.1f, 2.0f, 1259158f})
    void cantCreateCourierFloatPassword(Float password) {
        String login = Shuffler.shuffle("ytueytu");
        String firstName = Shuffler.shuffle("firstName");
        String errorMessage = String.format(
                "User was created with float %f password value",
                password
        );


        Courier<String, Float, String> courier = new Courier<>(login, password, firstName);

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

    @ParameterizedTest
    @DisplayName("POST /api/v1/courier doesn't create a courier (bad firstName type)")
    @Description("Negative test for creating a courier with float firstName, status code 400")
    @ValueSource(floats = {1234.0f, 0.0f, 1.0f, 0.1f, -0.1f, 2.0f, 1259158f})
    void cantCreateCourierFloatFirstName(Float firstName) {
        String login = Shuffler.shuffle("twoiuqi");
        String password = Shuffler.shuffle("ololoev");
        String errorMessage = String.format(
                "User was created with float %f firstName value",
                firstName
        );

        Courier<String, String, Float> courier = new Courier<>(login, password, firstName);

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
