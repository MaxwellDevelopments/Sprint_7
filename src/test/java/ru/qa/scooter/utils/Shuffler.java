package ru.qa.scooter.utils;

import java.util.Random;

public class Shuffler {
    private Shuffler() {

    }

    public static String shuffle(String s) {
        int length = s.length();
        char[] tempArray = s.toCharArray();

        for (int i = 0; i < length; i++) {
            int randIndex = new Random().nextInt(length);
            char temp = tempArray[i];
            tempArray[i] = tempArray[randIndex];
            tempArray[randIndex] = temp;
        }
        return new String(tempArray);
    }

    public static String changeHalves(String s) {
        return s.substring(s.length() / 2) + s.substring(0, s.length() / 2);
    }
}
