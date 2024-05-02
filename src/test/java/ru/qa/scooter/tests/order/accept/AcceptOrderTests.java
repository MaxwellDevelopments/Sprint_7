package ru.qa.scooter.tests.order.accept;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.qa.scooter.business.pojo.courier.Courier;
import ru.qa.scooter.business.pojo.courier.CourierId;
import ru.qa.scooter.business.pojo.courier.CourierWithoutFirstName;
import ru.qa.scooter.business.pojo.order.Order;
import ru.qa.scooter.business.pojo.order.OrderId;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.requests.Postcondition;
import ru.qa.scooter.utils.api.requests.Precondition;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.utils.Shuffler;
import ru.qa.scooter.utils.api.requests.CourierApi;
import ru.qa.scooter.utils.api.requests.OrderApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.is;

@Epic("Order accept.")
class AcceptOrderTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = Constants.LINK;
    }

    @Test
    @DisplayName("PUT /api/v1/orders/accept/:id?courierId=:id")
    @Description("Can accept order when courier and order are existed. Status code 200")
    void canAcceptOrderWithExistedCourierAndExistedOrder() {
        String login = Shuffler.shuffle("gajshtp");
        String password = Shuffler.shuffle("5125");
        String firstName = Shuffler.shuffle("hewryi");

        String jsonPath = "ok";
        Boolean expected = true;

        Precondition.Courier.create(new Courier<>(login, password, firstName));

        int courierId = CourierApi.getId(new CourierWithoutFirstName<>(login, password));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        firstName = "Itachi";
        String lastName = "Uchiha";
        String address = "Uchiha clan";
        int metroStation = 4;
        String phone = "+7 800 555 35 35";
        int rentTime = 3;
        String deliveryDate = LocalDate.now().plusDays(2).format(formatter);
        String comment = "Next time, Saske";
        List<String> colorsList = List.of("BLACK");

        Order order = new Order(
                firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                colorsList
        );

        Response responseMakeOrder = Precondition.Order.create(order);

        int orderId = OrderApi.getId(responseMakeOrder);

        Response responseAccept = OrderApi.acceptOrder(orderId, courierId);
        try {
            Checkers.check200Success(responseAccept);
            Checkers.checkAnswerInResponse(responseAccept, jsonPath, is(expected));
        } catch (AssertionError e) {
            Postcondition.Courier.delete(courierId);
            throw e;
        }

        Postcondition.Courier.delete(courierId);
    }


    @Test
    @DisplayName("PUT /api/v1/orders/accept/:id?courierId=:id")
    @Description("Can't accept order when courier is not existed. Status code 404")
    void cantAcceptOrderWithNonExistedCourierAndExistedOrder() {
        int courierId = 999999999;
        String jsonPath = "message";
        String expected = "Курьера с таким id не существует";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String firstName = "Itachi";
        String lastName = "Uchiha";
        String address = "Uchiha clan";
        int metroStation = 4;
        String phone = "+7 800 555 35 35";
        int rentTime = 3;
        String deliveryDate = LocalDate.now().plusDays(2).format(formatter);
        String comment = "Next time, Saske";
        List<String> colorsList = List.of("BLACK");

        Order order = new Order(
                firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                colorsList
        );

        Response responseMakeOrder = Precondition.Order.create(order);

        int orderId = OrderApi.getId(responseMakeOrder);

        Response responseAccept = OrderApi.acceptOrder(orderId, courierId);
        try {
            Checkers.check404NotFound(responseAccept);
            Checkers.checkAnswerInResponse(responseAccept, jsonPath, is(expected));
        } catch (AssertionError e) {
            throw e;
        }
    }

    @Test
    @DisplayName("PUT /api/v1/orders/accept/:id?courierId=:id")
    @Description("Can't accept order when order is not existed. Status code 404")
    void cantAcceptOrderWithExistedCourierAndNonExistedOrder() {
        String login = Shuffler.shuffle("agksja");
        String password = Shuffler.shuffle("15829");
        String firstName = Shuffler.shuffle("gkjasas");

        Precondition.Courier.create(new Courier<>(login, password, firstName));

        int orderId = 999999999;
        String jsonPath = "message";
        String expected = "Заказа с таким id не существует";

        int courierId = CourierApi.getId(new CourierWithoutFirstName<>(login, password));


        Response responseAccept = OrderApi.acceptOrder(orderId, courierId);
        try {
            Checkers.check404NotFound(responseAccept);
            Checkers.checkAnswerInResponse(responseAccept, jsonPath, is(expected));
        } catch (AssertionError e) {
            Postcondition.Courier.delete(courierId);
            throw e;
        }

        Postcondition.Courier.delete(courierId);
    }


    @Test
    @DisplayName("PUT /api/v1/orders/accept/:id?courierId=:id")
    @Description("Can't accept order when missed parameter (courierId). Status code 404")
    void cantAcceptOrderWithExistedCourierAndWithoutOrderId() {
        String login = Shuffler.shuffle("agksja");
        String password = Shuffler.shuffle("15829");
        String firstName = Shuffler.shuffle("gkjasas");

        String jsonPath = "message";
        String expected = "Недостаточно данных для поиска";

        Precondition.Courier.create(new Courier<>(login, password, firstName));
        int courierId = CourierApi.getId(new CourierWithoutFirstName<>(login, password));

        Response responseAccept = OrderApi.acceptOrder(new CourierId(courierId));
        try {
            Checkers.check400BadRequest(responseAccept);
            Checkers.checkAnswerInResponse(responseAccept, jsonPath, is(expected));
        } catch (AssertionError e) {
            Postcondition.Courier.delete(courierId);
            throw e;
        }

        Postcondition.Courier.delete(courierId);
    }

    @Test
    @DisplayName("PUT /api/v1/orders/accept/:id?courierId=:id")
    @Description("Can't accept order when missed parameter (courierId). Status code 404")
    void cantAcceptOrderWithoutCourierIdAndWithExistedOrder() {
        String jsonPath = "message";
        String expected = "Недостаточно данных для поиска";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String firstName = "Itachi";
        String lastName = "Uchiha";
        String address = "Uchiha clan";
        int metroStation = 4;
        String phone = "+7 800 555 35 35";
        int rentTime = 3;
        String deliveryDate = LocalDate.now().plusDays(2).format(formatter);
        String comment = "Next time, Saske";
        List<String> colorsList = List.of("BLACK");

        Order order = new Order(
                firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                colorsList
        );

        Response responseMakeOrder = Precondition.Order.create(order);

        int orderId = OrderApi.getId(responseMakeOrder);


        Response responseAccept = OrderApi.acceptOrder(new OrderId(orderId));
        Checkers.check400BadRequest(responseAccept);
        Checkers.checkAnswerInResponse(responseAccept, jsonPath, is(expected));
    }

}
