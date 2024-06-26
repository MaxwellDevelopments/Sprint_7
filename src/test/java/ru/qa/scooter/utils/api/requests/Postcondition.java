package ru.qa.scooter.utils.api.requests;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.qa.scooter.utils.api.checkers.Checkers;


public class Postcondition {

    private Postcondition(){

    }

    public static class Courier {
        private Courier() {

        }

        @Step("Postcondition: delete courier from database")
        public static <T, V, K> void delete(ru.qa.scooter.business.pojo.courier.Courier<T, V, K> courier) {
            Response responseDelete = CourierApi.delCourier(courier);
            Checkers.check200Success(responseDelete);
        }

        @Step("Postcondition: delete courier from database")
        public static void delete(int courierId) {
            Response responseDelete = CourierApi.delCourier(courierId);
            Checkers.check200Success(responseDelete);
        }

    }


}