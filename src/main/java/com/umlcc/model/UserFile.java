package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A file for a UML Data or My Code project. Can represent a .java file through
 * its classes HashMap or any other file through its content.
 * @author Joe Hardy
 */
public class UserFile {
    private String name;
    private ArrayList<JavaClass> classes;
    private ModificationRule modRule;
    private String content;

    /**
     * Constructor for a UserFile representing a generic text file.
     * @param name the name of this file.
     * @param content all of the lines of this file represented as a String.
     */
    public UserFile(String name, String content) {
        this.name = name;
        this.content = content;
    }

    /**
     * Constructor for a UserFile representing a JavaFile.
     * @param name
     * @param classes
     * @param modRule
     * @param content
     */
    public UserFile(String name, ArrayList<JavaClass> classes, ModificationRule modRule, String content) {
        this.name = name;
        this.classes = classes;
        this.modRule = modRule;
        this.content = content;
    }

    /**
     * Accessor method for name.
     * @return this file's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Accesses a single class of a given name.
     * @param name the name of the class to access.
     * @return the class.
     */
    public JavaClass getClass(String name) {
        for (JavaClass javaClass : classes) {
            if (javaClass.getName().equals(name)) return javaClass;
        }
        return null;
    }

    /**
     * Accesses the public class of this file, which will have the same name as
     * the file.
     * @return the public class of this file.
     */
    public JavaClass getPublicClass() {
        return getClass(name.split(".java")[0]);
    }

    /**
     * Checks the compliance of this file against the Config file's rules.
     * @return the list of EvaluationResults resulting from this compliance check.
     */
    public ArrayList<EvaluationResult> checkCompliance() {
        ArrayList<EvaluationResult> results = new ArrayList<EvaluationResult>();

        for (JavaClass jclass : classes)
            results.addAll(jclass.checkCompliance());

        return results;
    }

    /**
     * Checks the compliance of this file against the Config file's rules
     * and another file.
     * @param other the other file to compare this one to.
     * @return the list of EvaluationResults resulting from this compliance check.
     */
    public ArrayList<EvaluationResult> checkCompliance(UserFile other) {
        ArrayList<EvaluationResult> results = new ArrayList<EvaluationResult>();

        for (JavaClass jClass : classes)
            results.addAll(jClass.checkCompliance(other.getClass(jClass.getName())));

        return results;
    }

    /**
     * Determines if this file is a Java file.
     * @return true if this has the .java file extension, false otherwise.
     */
    public boolean isJavaFile() {
        return name.endsWith(".java");
    }

    @Override
    public String toString() {
        if (!isJavaFile()) return getName();
        String s = getName() + " {";
        if (classes != null) {
            for (JavaClass jClass : classes) {
                s += "\n" + jClass;
            }
        }
        return s + "\n}";
    }
}
