package ru.qa.scooter.utils.api.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.qa.scooter.business.pojo.courier.CourierId;
import ru.qa.scooter.business.pojo.order.Order;
import ru.qa.scooter.business.pojo.order.OrderId;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.responses.ResponseWithToString;

import static io.restassured.RestAssured.given;

public class OrderApi {

    private final static String makeOrderEndPoint = "/api/v1/orders/";

    private final static String acceptOrderEndPoint = "/api/v1/orders/accept/";

    private static String getMakeOrderEndPoint() {
        return makeOrderEndPoint;
    }

    private static String getMakeOrderBody(Response response) {
        return String.format(
                "Make order POST %s. \nBody:\n%s",
                getMakeOrderEndPoint(),
                response.body().asPrettyString()
        );
    }

    private static String getAcceptOrderEndPoint(int orderId) {
        return String.format(
                "%s%d",
                acceptOrderEndPoint,
                orderId
        );
    }

    private static String getAcceptOrderEndPoint() {
        return acceptOrderEndPoint;
    }

    private static String getAcceptOrderFullEndPoint(int orderId, int courierId) {
        return String.format(
                "%s%d?courierId=%d",
                acceptOrderEndPoint,
                orderId,
                courierId
        );
    }

    private static String getAcceptOrderBody(Response response, int orderId, int courierId) {
        return String.format(
                "Login courier POST %s. \nBody:\n%s",
                getAcceptOrderFullEndPoint(orderId, courierId),
                response.body().asPrettyString()
        );
    }

    private static String getAcceptOrderBody(Response response, OrderId orderId) {
        return String.format(
                "Login courier POST %s. \nBody:\n%s",
                getAcceptOrderEndPoint(orderId.getTrack()),
                response.body().asPrettyString()
        );
    }

    private static String getAcceptOrderBody(Response response, CourierId courierId) {
        return String.format(
                "Login courier POST \"%s?courierId=%d\",. \nBody:\n%s",
                getAcceptOrderEndPoint(),
                courierId.getId(),
                response.body().asPrettyString()
        );
    }



    @Step("Precondition: create order")
    protected static Response preconditionCreateOrder(Order order) {
        Response response = makeOrder(order);
        Checkers.check201Created(response);
        return response;
    }

    @Step("Make order. POST /api/v1/orders")
    public static Response makeOrder(Order order) {
        Response response = given()
                                .header("Content-type", "application/json")
                                .and()
                                .body(order)
                                .when()
                                .post(getMakeOrderEndPoint());

        return new ResponseWithToString(response, getMakeOrderBody(response));
    }

    @Step("Accept order. PUT /api/v1/orders/accept/:id")
    public static Response acceptOrder(int orderId, int courierid) {
        Response response = given()
                                .queryParam("courierId", String.valueOf(courierid))
                                .when()
                                .put(getAcceptOrderEndPoint(orderId));

        return new ResponseWithToString(response, getAcceptOrderBody(response, orderId, courierid));
    }

    @Step("Accept order without orderId. PUT /api/v1/orders/accept/:id")
    public static Response acceptOrder(CourierId courier) {
        Response response = given()
                .queryParam("courierId", courier.getId())
                .put(getAcceptOrderEndPoint());

        return new ResponseWithToString(response, getAcceptOrderBody(response, courier));
    }

    @Step("Accept order without courierId. PUT /api/v1/orders/accept/:id")
    public static Response acceptOrder(OrderId orderId) {
        Response response = given()
                .put(getAcceptOrderEndPoint(orderId.getTrack()));

        return new ResponseWithToString(response, getAcceptOrderBody(response, orderId));
    }

    @Step("Get order id")
    public static int getId(Response response) {
        return response.body().as(OrderId.class).getTrack();
    }
}
