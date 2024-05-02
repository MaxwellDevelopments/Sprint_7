package ru.qa.scooter.utils.api.checkers;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class Checkers {

    @Step("Check response message")
    public static <T> void checkAnswerInResponse(Response response, String jsonPath, Matcher<? super T> matcher) {
        response.then().assertThat().body(jsonPath, matcher);
    }


    @Step("Check response message")
    public static <T> void checkAnswerInResponse(Response response, String jsonPath, T expected) {
        response.then().assertThat().body(jsonPath, is(expected));
    }

    @Step("Check response message")
    public static void checkAnswerInResponseIsNotNull(Response response, String jsonPath) {
        response.then().assertThat().body(jsonPath, notNullValue());
    }

    @Step("Check response has status 201")
    public static void check201Created(Response response) {
        response.then().assertThat().statusCode(201);
    }

    @Step("Check response has status 200")
    public static void check200Success(Response response) {
        response.then().assertThat().statusCode(200);
    }

    @Step("Check response has status 400")
    public static void check400BadRequest(Response response) {
        response.then().assertThat().statusCode(400);
    }

    @Step("Check response has status 409")
    public static void check409Conflict(Response response) {
        response.then().assertThat().statusCode(409);
    }

    @Step("Check response has status 404")
    public static void check404NotFound(Response response) {
        response.then().assertThat().statusCode(404);
    }
}
