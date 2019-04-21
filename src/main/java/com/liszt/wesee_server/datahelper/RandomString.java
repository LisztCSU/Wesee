package com.liszt.wesee_server.datahelper;

import java.util.Random;

public class RandomString {

    String STR1 = "0123456789";

    public static String randomString(int length) {
        String STR1 = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = STR1.charAt(new Random().nextInt(STR1.length()));
            sb.append(ch);
        }
        return sb.toString();


    }
}