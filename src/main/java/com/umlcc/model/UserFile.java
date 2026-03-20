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
    private HashMap<String, JavaClass> classes;
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
     * Constructor for a UserFile representing a .java file.
     * @param name the name of this file.
     * @param classes the classes of this file. typically just a single class.
     * @param modRule the modification rule of this file.
     * @param content all of the lines of this file represented as a String.
     */
    public UserFile(String name, ArrayList<JavaClass> classes, ModificationRule modRule, String content) {
        this.name = name;
        this.classes = new HashMap<String, JavaClass>();
        for (JavaClass jclass : classes) {
            this.classes.put(jclass.getName(), jclass);
        }
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
     * @param name the name of the file to access.
     * @return the file.
     */
    public JavaClass getClass(String name) {
        return classes.get(name);
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
        return null;
    }

    /**
     * Checks the compliance of this file against the Config file's rules
     * and another file.
     * @param other the other file to compare this one to.
     * @return the list of EvaluationResults resulting from this compliance check.
     */
    public ArrayList<EvaluationResult> checkCompliance(UserFile other) {
        return null;
    }
}
