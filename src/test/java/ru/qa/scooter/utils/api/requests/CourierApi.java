package ru.qa.scooter.utils.api.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.qa.scooter.business.pojo.courier.AbstractCourier;
import ru.qa.scooter.business.pojo.courier.CourierId;
import ru.qa.scooter.utils.api.responses.ResponseWithToString;

import static io.restassured.RestAssured.given;

public class CourierApi {

    private final static String CREATE_ENDPOINT = "/api/v1/courier/";

    private final static String LOGIN_ENDPOINT = "/api/v1/courier/login/";

    private final static String DELETE_ENDPOINT = CREATE_ENDPOINT;

    public static String getCreateEndpoint() {
        return CREATE_ENDPOINT;
    }

    public static String getLoginEndpoint() {
        return LOGIN_ENDPOINT;
    }

    public static String getDeleteEndPoint(int id) {
        return String.format("%s%d", DELETE_ENDPOINT, id);
    }

    public static String getDeleteEndpoint() {
        return DELETE_ENDPOINT;
    }

    private static String getCreateResponseBody(Response response) {
        return String.format(
                "Create courier POST %s. \nBody:\n%s",
                getCreateEndpoint(),
                response.body().asPrettyString()
        );
    }

    private static String getLoginResponseBody(Response response) {
        return String.format(
                "Login courier POST %s. \nBody:\n%s",
                getLoginEndpoint(),
                response.body().asPrettyString()
        );
    }

    private static String getDeleteResponseBody(Response response, int courierId) {
        return String.format(
                "Delete courier POST %s. \nBody:\n%s",
                getDeleteEndPoint(courierId),
                response.body().asPrettyString()
        );
    }

    @Step("Get courier id")
    public static int getId(AbstractCourier courier) {
        return loginCourier(courier).as(CourierId.class).getId();
    }

    @Step("Login courier. Send POST /api/v1/courier/login")
    public static Response loginCourier(AbstractCourier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(getLoginEndpoint());

        return new ResponseWithToString(response,
                getLoginResponseBody(response));
    }

    @Step("Delete courier. Send DELETE /api/v1/courier/id")
    public static Response delCourier(int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(getDeleteEndPoint(id));
    }

    @Step("Delete courier without id. Send DELETE /api/v1/courier/")
    public static Response delCourier() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(getDeleteEndpoint());
    }


    public static Response delCourier(AbstractCourier courier) {
        int id = CourierApi.getId(courier);
        Response response = delCourier(id);

        return new ResponseWithToString(response,
                getDeleteResponseBody(response, id));
    }

    @Step("Create courier. Send POST /api/v1/courier")
    public static Response createCourier(AbstractCourier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(getCreateEndpoint());

        return new ResponseWithToString(response,
                getCreateResponseBody(response));
    }


}
