package com.umlcc.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Tester {
    private static ComplianceCheckerApplication app;

    public static void main(String[] args) {
        app = ComplianceCheckerApplication.getInstance();

        app.close();
    }

    public static void setupconfig() {
        HashMap<Warning, String> wm = new HashMap<Warning, String>();
        wm.put(Warning.NONE, "No problems here!");
        wm.put(Warning.NO_JAVADOC, "Missing JavaDoc.");
        wm.put(Warning.NO_JAVADOC_METHOD, "Missing JavaDoc in a method.");
        wm.put(Warning.NO_JAVADOC_PARAMETER, "Missing a @parameter in a method JavaDoc.");
        wm.put(Warning.NO_JAVADOC_RETURN, "Missing a @return in a method JavaDoc.");
        wm.put(Warning.NO_JAVADOC_AUTHOR, "Missing @author in your class summary JavaDoc.");
        wm.put(Warning.INCORRECT_PRIVACY_ATTRIBUTE, "Incorrect privacy for an attribute.");
        wm.put(Warning.INCORRECT_PRIVACY_METHOD, "Incorrect privacy for a method.");
        wm.put(Warning.NOT_FOLLOWING_UML, "Not following the UML correctly.");
        wm.put(Warning.EXTRA_CLASS_ATTRIBUTE, "Missing JavaDoc.");
        wm.put(Warning.MISSING_FILE, "Missing file.");
        wm.put(Warning.EXTRA_FILE, "Extra file.");
        wm.put(Warning.INDENTATION_ISSUES, "Indentation issues.");
        wm.put(Warning.SPACING_ISSUES, "Spacing issues.");
        app.setConfig(wm);

    }

    public static void test4() {
        app.loadUmlDataByUmlcc("state.umlcc");
        ArrayList<String> s = app.checkCompliance("/media/jsaur/Storage/Class/SCHC-497/DPA-student-tests/design-patterns/state");
        System.out.println(s);
    }


}
