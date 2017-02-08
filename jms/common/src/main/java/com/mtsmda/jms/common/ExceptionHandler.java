package com.mtsmda.jms.common;

/**
 * Created by dminzat on 1/31/2017.
 */
public class ExceptionHandler {

    public static <T extends Exception> String toString(T tException) {
        return "Class - " + tException.getClass().getCanonicalName() + ", message - " + tException.getMessage()
                + ", exception - " + tException.toString();
    }

    public static <T extends Exception> void toStringOut(T tException) {
        System.out.println(toString(tException));
    }
}