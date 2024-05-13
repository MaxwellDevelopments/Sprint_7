package ru.qa.scooter.utils.global;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;

import java.util.Random;

public class Constants {
    public final static String LINK = "https://qa-scooter.praktikum-services.ru";
    public static final Faker FAKER = new Faker();
    public static final String PASS_REGEX = String.format(
            "\\d+{%d}[a-z]+{%d}",
            new Random().nextInt(2) + 1,
            new Random().nextInt(2) + 1
    );

    private Constants() {

    }


}
