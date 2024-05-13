package ru.qa.scooter.utils.api.requests;


import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.qa.scooter.utils.api.checkers.Checkers;

public class Precondition {


    private Precondition(){

    }


    public static class Courier {
        private Courier() {

        }

        @Step("Precondition: create courier")
        public static <T, V, K> Response create(ru.qa.scooter.business.pojo.courier.Courier<T, V, K> courier) {
            Response response = CourierApi.createCourier(courier);
            Checkers.check201Created(response);
            return response;
        }

    }

    public static class Order {
        private Order() {

        }

        @Step("Precondition: create order")
        public static Response create(ru.qa.scooter.business.pojo.order.Order order) {
            Response response = OrderApi.makeOrder(order);
            Checkers.check201Created(response);
            return response;
        }
    }
}
