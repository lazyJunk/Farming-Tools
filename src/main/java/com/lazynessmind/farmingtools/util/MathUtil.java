package com.lazynessmind.farmingtools.util;

public class MathUtil {

    //java random is popo
    public static int random(int min, int max){
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
