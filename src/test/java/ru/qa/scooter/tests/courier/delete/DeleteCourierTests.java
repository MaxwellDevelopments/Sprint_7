package ru.qa.scooter.tests.courier.delete;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.qa.scooter.business.pojo.courier.Courier;
import ru.qa.scooter.business.pojo.courier.CourierWithoutFirstName;
import ru.qa.scooter.tests.base.BaseScooter;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.requests.Precondition;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.utils.api.requests.CourierApi;

import static org.hamcrest.Matchers.is;

@Epic("Courier delete.")
class DeleteCourierTests extends BaseScooter {

    @Test
    @DisplayName("DELETE /api/v1/courier/:id trying delete an existed courier")
    @Description("Test message in response and status code 200")
    void deleteExistedCourier() {
        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String firstName = Constants.FAKER.name().firstName();
        String jsonPath = "ok";

        Precondition.Courier.create(new Courier<>(login, password, firstName));

        int courierId = CourierApi.getId(new CourierWithoutFirstName<>(login, password));

        Response response = CourierApi.delCourier(courierId);
        Checkers.check200Success(response);
        Checkers.checkAnswerInResponse(response, jsonPath, is(true));
    }

    @Test
    @DisplayName("DELETE /api/v1/courier/:id trying delete non-existed courier")
    @Description("Test message in response and status code 404")
    void deleteNonExistedCourier() {
        String jsonPath = "message";
        String expected = "Курьера с таким id нет";

        Response response = CourierApi.delCourier(999999999);
        Checkers.check404NotFound(response);
        Checkers.checkAnswerInResponse(response, jsonPath, is(expected));
    }

    @Test
    @DisplayName("DELETE /api/v1/courier/:id with missed id")
    @Description("Test message in response and status code 400")
    void deleteCourierWithoutId() {
        String jsonPath = "message";
        String expected = "Недостаточно данных для удаления курьера";

        Response response = CourierApi.delCourier();
        Checkers.check400BadRequest(response);
        Checkers.checkAnswerInResponse(response, jsonPath, is(expected));
    }
}
