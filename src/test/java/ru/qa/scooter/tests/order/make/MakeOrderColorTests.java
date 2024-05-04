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
import java.util.Random;
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
        Random randInt = new Random();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String firstName = Constants.FAKER.name().firstName();
        String lastName = Constants.FAKER.name().lastName();
        String address = Constants.FAKER.address().streetAddress();
        int metroStation = randInt.nextInt(4) + 1;
        String phone = Constants.FAKER.phoneNumber().cellPhone();
        int rentTime = randInt.nextInt(6) + 1;
        String deliveryDate = LocalDate.now().plusDays(randInt.nextInt(2) + 1).format(formatter);
        String comment = Constants.FAKER.letterify("???????????");
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
