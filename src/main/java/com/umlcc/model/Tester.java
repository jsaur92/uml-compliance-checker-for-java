package com.umlcc.model;

public class Tester {
    public static void main(String[] args) {
        ComplianceCheckerApplication app = ComplianceCheckerApplication.getInstance();
        Directory dir = app.loadUmlDataByUmlcc("strategy.umlcc");
        System.out.println("START");
        System.out.println(dir);
        System.out.println("END");
    }
}
