package com.mtsmda.jms.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by dminzat on 1/31/2017.
 */
public class ReadFromSystemIn {

    public static void readSystemIn() {
        System.out.print("Enter message(write 'quit' or shortcut 'q' for exit):\n");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean quitNow = false;
            String line = null;
            do {
                line = bufferedReader.readLine();
                if (null != line && !line.trim().isEmpty()) {
                    quitNow = line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("q");
                }
                if(!quitNow){
                    System.out.println(line);
                }else{
                    System.out.println("SEE YOU SOON!");
                }
            }
            while (!quitNow);
        } catch (Exception e) {
            ExceptionHandler.toString(e);
        }
    }

    public static void main(String[] args) {
        readSystemIn();
    }

}