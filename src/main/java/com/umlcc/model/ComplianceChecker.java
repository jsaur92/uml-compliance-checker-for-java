package com.umlcc.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Singleton for checking the compliance between two projects.
 * @author Joe Hardy
 */
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
        File umlcc = new File(pathname);
        try {
            FileReader uReader = new FileReader(umlcc);
            String allLines = uReader.readAllAsString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public ArrayList<String> checkCompliance(String rootname) {
        return null;
    }
}
