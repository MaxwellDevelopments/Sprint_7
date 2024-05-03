package ru.qa.scooter.tests.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import ru.qa.scooter.utils.global.Constants;

public abstract class BaseScooter {
    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = Constants.LINK;
    }
}
