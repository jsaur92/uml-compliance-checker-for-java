package com.umlcc.model;

import java.io.File;
import java.util.ArrayList;

public class Tester {
    private static ComplianceCheckerApplication app;

    public static void main(String[] args) {
        app = ComplianceCheckerApplication.getInstance();
        test4();
    }

    public static void test4() {
        app.loadUmlDataByUmlcc("state.umlcc");
        ArrayList<String> s = app.checkCompliance("/media/jsaur/Storage/Class/SCHC-497/DPA-student-tests/design-patterns/state");
        System.out.println(s);
    }


}
