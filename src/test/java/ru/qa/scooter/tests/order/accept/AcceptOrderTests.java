package ru.qa.scooter.tests.order.accept;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.qa.scooter.business.pojo.courier.Courier;
import ru.qa.scooter.business.pojo.courier.CourierId;
import ru.qa.scooter.business.pojo.courier.CourierWithoutFirstName;
import ru.qa.scooter.business.pojo.order.Order;
import ru.qa.scooter.business.pojo.order.OrderId;
import ru.qa.scooter.tests.base.BaseScooter;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.api.requests.Postcondition;
import ru.qa.scooter.utils.api.requests.Precondition;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.utils.api.requests.CourierApi;
import ru.qa.scooter.utils.api.requests.OrderApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.is;

@Epic("Order accept.")
class AcceptOrderTests extends BaseScooter {

    private static Order getOrder(String firstName, List<String> colors) {
        Random randInt = new Random();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String lastName = Constants.FAKER.name().lastName();
        String address = Constants.FAKER.address().streetAddress();
        int metroStation = randInt.nextInt(4) + 1;
        String phone = Constants.FAKER.phoneNumber().cellPhone();
        int rentTime = randInt.nextInt(6) + 1;
        String deliveryDate = LocalDate.now().plusDays(randInt.nextInt(2) + 1).format(formatter);
        String comment = Constants.FAKER.letterify("???????????");

        return new Order(
                firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                colors
        );
    }

    @Test
    @DisplayName("PUT /api/v1/orders/accept/:id?courierId=:id")
    @Description("Can accept order when courier and order are existed. Status code 200")
    void canAcceptOrderWithExistedCourierAndExistedOrder() {
        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String firstName = Constants.FAKER.name().firstName();

        String jsonPath = "ok";
        Boolean expected = true;
        int statusCode = 200;

        Precondition.Courier.create(new Courier<>(login, password, firstName));

        int courierId = CourierApi.getId(new CourierWithoutFirstName<>(login, password));

        List<String> colorsList = List.of("BLACK");
        Order order = getOrder(firstName, colorsList);

        Response responseMakeOrder = Precondition.Order.create(order);

        int orderId = OrderApi.getId(responseMakeOrder);

        Response responseAccept = OrderApi.acceptOrder(orderId, courierId);
        checkStatusTAndMessageElseDeleteCourierAndThrowException(
                responseAccept,
                statusCode,
                jsonPath,
                expected,
                courierId
        );

        Postcondition.Courier.delete(courierId);
    }

    @Step("Check response has status T and message. If status is not T then delete courier")
    public <T> void checkStatusTAndMessageElseDeleteCourierAndThrowException(
            Response response,
            int statusCode,
            String jsonPath,
            T expected,
            int courierId) {

        try {
            Checkers.checkTStatus(response, statusCode);
            Checkers.checkAnswerInResponse(response, jsonPath, is(expected));
        } catch (AssertionError e) {
            Postcondition.Courier.delete(courierId);
            throw e;
        }
    }


    @Test
    @DisplayName("PUT /api/v1/orders/accept/:id?courierId=:id")
    @Description("Can't accept order when courier is not existed. Status code 404")
    void cantAcceptOrderWithNonExistedCourierAndExistedOrder() {
        int courierId = 999999999;
        String jsonPath = "message";
        String expected = "Курьера с таким id не существует";

        Order order = getOrder("Itachi", List.of("BLACK"));

        Response responseMakeOrder = Precondition.Order.create(order);

        int orderId = OrderApi.getId(responseMakeOrder);

        Response responseAccept = OrderApi.acceptOrder(orderId, courierId);
        Checkers.check404NotFound(responseAccept);
        Checkers.checkAnswerInResponse(responseAccept, jsonPath, is(expected));

    }

    @Test
    @DisplayName("PUT /api/v1/orders/accept/:id?courierId=:id")
    @Description("Can't accept order when order is not existed. Status code 404")
    void cantAcceptOrderWithExistedCourierAndNonExistedOrder() {
        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String firstName = Constants.FAKER.name().firstName();

        int statusCode = 404;

        Precondition.Courier.create(new Courier<>(login, password, firstName));

        int orderId = 999999999;
        String jsonPath = "message";
        String expected = "Заказа с таким id не существует";

        int courierId = CourierApi.getId(new CourierWithoutFirstName<>(login, password));


        Response responseAccept = OrderApi.acceptOrder(orderId, courierId);
        checkStatusTAndMessageElseDeleteCourierAndThrowException(
                responseAccept,
                statusCode,
                jsonPath,
                expected,
                courierId
        );

        Postcondition.Courier.delete(courierId);
    }


    @Test
    @DisplayName("PUT /api/v1/orders/accept/:id?courierId=:id")
    @Description("Can't accept order when missed parameter (courierId). Status code 404")
    void cantAcceptOrderWithExistedCourierAndWithoutOrderId() {
        String login = Constants.FAKER.name().username();
        String password = Constants.FAKER.regexify(Constants.PASS_REGEX);
        String firstName = Constants.FAKER.name().firstName();

        int statusCode = 404;

        String jsonPath = "message";
        String expected = "Недостаточно данных для поиска";

        Precondition.Courier.create(new Courier<>(login, password, firstName));
        int courierId = CourierApi.getId(new CourierWithoutFirstName<>(login, password));

        Response responseAccept = OrderApi.acceptOrder(new CourierId(courierId));
        checkStatusTAndMessageElseDeleteCourierAndThrowException(
                responseAccept,
                statusCode,
                jsonPath,
                expected,
                courierId
        );

        Postcondition.Courier.delete(courierId);
    }

    @Test
    @DisplayName("PUT /api/v1/orders/accept/:id?courierId=:id")
    @Description("Can't accept order when missed parameter (courierId). Status code 404")
    void cantAcceptOrderWithoutCourierIdAndWithExistedOrder() {
        String jsonPath = "message";
        String expected = "Недостаточно данных для поиска";

        Order order = getOrder("Itachi", List.of("GREY"));

        Response responseMakeOrder = Precondition.Order.create(order);

        int orderId = OrderApi.getId(responseMakeOrder);

        Response responseAccept = OrderApi.acceptOrder(new OrderId(orderId));
        Checkers.check400BadRequest(responseAccept);
        Checkers.checkAnswerInResponse(responseAccept, jsonPath, is(expected));
    }

}
