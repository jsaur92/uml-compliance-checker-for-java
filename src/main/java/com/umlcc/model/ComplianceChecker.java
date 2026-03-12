package com.umlcc.model;

import java.util.ArrayList;

public class ComplianceChecker {
    private static ComplianceChecker complianceChecker;
    private Directory umlData;

    private ComplianceChecker() {

    }

    public static ComplianceChecker getInstance() {
        return null;
    }

    public boolean loadUmlDataByRepo(String rootname) {
        return false;
    }

    public boolean loadUmlDataByUmlcc(String pathname) {
        return false;
    }

    public ArrayList<String> checkCompliance(String rootname) {
        return null;
    }
}
