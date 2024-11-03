package com.microservices.app.display;

public class ConsolePrint implements Print {
    @Override
    public void printData(String data) {
        System.out.println(data);
    }
}
