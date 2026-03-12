package com.umlcc.model;

import java.util.ArrayList;

public class ComplianceChecker {
    private static ComplianceChecker complianceChecker;
    private Directory umlData;

    /**
     * Constructor for the ComplianceChecker class.
     */
    private ComplianceChecker() {

    }

    /**
     * Get the static ComplianceChecker instance of this session, or make one
     * if there isn't one already.
     * @return the ComplianceChecker instance.
     */
    public static ComplianceChecker getInstance() {
        if (complianceChecker == null) {
            complianceChecker = new ComplianceChecker();
        }
        return complianceChecker;
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
