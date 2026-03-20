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

    private Directory loadDirFromUmlcc(String dirText) {
        return null;
    }

    private UserFile loadFileFromUmlcc(String fileText) {
        return null;
    }

    private JavaClass loadClassFromUmlcc(String classText) {
        String[] lines = classText.split("\n");

        String classLine = lines[0].replaceAll("\t", "");
        String[] parts = classLine.split(" ");
        String name = parts[parts.length-1];
        ArrayList<Modifier> mods = new ArrayList<Modifier>();
        for (int i = 0; i < parts.length-2; i++) {
            mods.add(Modifier.valueOf(parts[i].toUpperCase()));
        }
        boolean is_interface = parts[parts.length-2].equals("interface");

        ArrayList<JavaVariable> vars = new ArrayList<JavaVariable>();
        ArrayList<JavaMethod> methods = new ArrayList<JavaMethod>();
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].charAt(lines[i].length()-1) == ')')
                methods.add(loadMethodFromUmlcc(lines[i]));
            else
                vars.add(loadVarFromUmlcc(lines[i]));
        }
        return new JavaClass(name, mods, "", vars, methods, is_interface);
    }

    private JavaMethod loadMethodFromUmlcc(String methodText) {
        int paramStart = methodText.indexOf('(');
        String partsFull = methodText.substring(0, paramStart);
        String[] parts = partsFull.split(" ");
        String name = parts[parts.length-1];
        String type = parts[parts.length-2];
        ArrayList<Modifier> mods = new ArrayList<Modifier>();
        for (int i = 0; i < parts.length-2; i++) {
            mods.add(Modifier.valueOf(parts[i].toUpperCase()));
        }

        String paramsFull = methodText.substring(paramStart, methodText.length()-1);
        String[] params = paramsFull.split(", ");
        ArrayList<JavaVariable> p = new ArrayList<JavaVariable>();
        for (String s : params) p.add(loadVarFromUmlcc(s));

        return new JavaMethod(name, mods, "", p, type, "");
    }

    private JavaVariable loadVarFromUmlcc(String varText) {
        String[] parts = varText.split(" ");
        String name = parts[parts.length-1];
        String type = parts[parts.length-2];
        ArrayList<Modifier> mods = new ArrayList<Modifier>();
        for (int i = 0; i < parts.length-2; i++) {
            mods.add(Modifier.valueOf(parts[i].toUpperCase()));
        }
        return new JavaVariable(name, mods, "", type);
    }

    public ArrayList<String> checkCompliance(String rootname) {
        return null;
    }
}
