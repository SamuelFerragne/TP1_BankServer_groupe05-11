package com.atoudeft.banque;

public class Util {
    public static boolean estAlphabetiqueMajuscule(char car) {
        return car>='A' && car<='Z';
    }
    public static boolean estChiffre(char car) {
        return car>='0' && car<='9';
    }
}
