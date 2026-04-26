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

    /**
     * Load a Directory into the umlData variable with a repository to a Java project.
     * @param rootname path to the root of the Java project repository.
     * @return true if successful.
     */
    public boolean loadUmlDataByRepo(String rootname) {
        if (!new File(rootname).exists()) return false;
        umlData = DataLoader.loadRepo(rootname);
        return true;
    }

    /**
     * Load a Directory into the umlData variable with a .umlcc file.
     * @param pathname path to the .umlcc file.
     * @return true if successful.
     */
    public boolean loadUmlDataByUmlcc(String pathname) {
        if (!new File(pathname).exists()) return false;
        umlData = DataLoader.loadUmlcc(pathname);
        return true;
    }

    /**
     * Reset the current umlData to be null.
     * @return true if successful.
     */
    public boolean clearUmlData() {
        umlData = null;
        return true;
    }

    /**
     * Check the UML compliance of a Java project. Compares against umlData if
     * umlData is not null.
     * @param rootname the Java project to check.
     * @return the output that shows every error/warning for the project.
     */
    public ArrayList<String> checkCompliance(String rootname) {
        Directory toCheck = DataLoader.loadRepo(rootname);
        ArrayList<EvaluationResult> results;
        if (umlData == null) results = toCheck.checkCompliance();
        else results = toCheck.checkCompliance(umlData);
        ArrayList<String> output = new ArrayList<String>();
        for (EvaluationResult result : results) {
            output.add(result.toString());
        }
        return output;
    }
}
