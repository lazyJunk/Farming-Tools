package com.lazynessmind.farmingtools.util;

public class TypeUtil {

    public static String getNameFromType(int type) {
        switch (type) {
            case 0:
                return "Iron";
            case 1:
                return "Diamond";
            case 2:
                return "Gold";
            case 3:
                return "Emerald";
        }
        return "I don´t know... Something is wrong!";
    }

    public static int getWorkTime(int type) {
        switch (type) {
            case 0:
                return 200;
            case 1:
                return 150;
            case 2:
                return 100;
            case 3:
                return 50;
        }
        return 69;
    }

    public static int energyExtractFromType(int type) {
        switch (type) {
            case 0:
                return 100;
            case 1:
                return 200;
            case 2:
                return 300;
            case 3:
                return 400;
        }
        return Integer.MAX_VALUE;
    }
}
