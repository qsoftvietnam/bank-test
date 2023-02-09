package com.java.bank.util;

import java.util.Random;

public class Util {

    public static int generateRandomNumberWithLength(int length) {
        int medium = (int) Math.pow(10, length - 1);
        return medium + new Random().nextInt(9 * medium);
    }
}
