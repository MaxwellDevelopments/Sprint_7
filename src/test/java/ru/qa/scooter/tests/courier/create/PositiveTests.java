package ru.qa.scooter.tests.courier.create;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.qa.scooter.utils.Shuffler;
import ru.qa.scooter.utils.api.requests.Postcondition;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.business.pojo.courier.Courier;
import ru.qa.scooter.business.pojo.courier.CourierWithoutFirstName;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.requests.CourierApi;

@Epic("Courier create. Positive tests")
class PositiveTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = Constants.LINK;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/PositiveCreateCourier.csv", numLinesToSkip = 0)
    @DisplayName("POST /api/v1/courier creates a courier")
    @Description("Positive tests for creating a courier, status code 201")
    void canCreateCourier(String login, String password, String firstName) {
        String jsonPath = "ok";
        Boolean expected = true;

        Courier<String, String, String> courier = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courier);
        Checkers.check201Created(responseCreate);
        Checkers.checkAnswerInResponse(responseCreate, jsonPath, expected);

        Postcondition.Courier.delete(
                new CourierWithoutFirstName<>(courier.getLogin(), courier.getPassword())
        );
    }

    @Test
    @DisplayName("POST /api/v1/courier doesn't create a courier with same login")
    @Description("Can't create a courier if courier with that login already existed, status code 409")
    void cantCreateCourierWithSameLogin() {
        String login = Shuffler.shuffle("agasgas");
        String password = Shuffler.shuffle("agasg");
        String firstName = Shuffler.shuffle("fhajs");

        String jsonPath = "ok";
        Boolean expected = true;

        Courier<String, String, String> courier = new Courier<>(login, password, firstName);

        Response responseCreate = CourierApi.createCourier(courier);
        Checkers.check201Created(responseCreate);
        Checkers.checkAnswerInResponse(responseCreate, jsonPath, expected);

        try {
            Response responseConflict = CourierApi.createCourier(courier);
            Checkers.check409Conflict(responseConflict);
            Checkers.checkAnswerInResponse(
                    responseConflict,
                    "message",
                    "Этот логин уже используется");
        }
        catch (AssertionError e) {
            Postcondition.Courier.delete(
                    new CourierWithoutFirstName<>(courier.getLogin(), courier.getPassword())
            );
            throw e;
        }
    }

}
