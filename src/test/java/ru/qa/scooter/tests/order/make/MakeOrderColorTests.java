package ru.qa.scooter.tests.order.make;

import io.qameta.allure.Epic;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.qa.scooter.business.pojo.order.Order;
import ru.qa.scooter.utils.api.checkers.Checkers;
import ru.qa.scooter.utils.global.Constants;
import ru.qa.scooter.utils.api.requests.OrderApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;

@Epic("Order make.")
class MakeOrderColorTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = Constants.LINK;
    }

    public static Stream<Arguments> getColorsData() {
        return Stream.of(
                Arguments.of(201, new String[]{"GREY", "BLACK"}),
                Arguments.of(201, new String[]{"GREY"}),
                Arguments.of(201, new String[]{"BLACK"}),
                Arguments.of(201, new String[0]),
                Arguments.of(400, new String[]{"STRANGE_COLOR"}),
                Arguments.of(400, new String[]{"BLACK, BLACK"}),
                Arguments.of(400, new String[]{"GREY, GREY"})
        );
    }

    @ParameterizedTest
    @MethodSource("getColorsData")
    void makeOrderTestDifferentColors(int statusCode, String... colors) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String firstName = "Itachi";
        String lastName = "Uchiha";
        String address = "Uchiha clan";
        int metroStation = 4;
        String phone = "+7 800 555 35 35";
        int rentTime = 3;
        String deliveryDate = LocalDate.now().plusDays(2).format(formatter);
        String comment = "Next time, Saske";
        List<String> colorsList = List.of(colors);

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

        Response response = OrderApi.makeOrder(order);
        if (statusCode == 201) {
            Checkers.check201Created(response);
            Checkers.checkAnswerInResponse(response, "track", notNullValue());
        } else {
            Checkers.check400BadRequest(response);
        }
    }
}
