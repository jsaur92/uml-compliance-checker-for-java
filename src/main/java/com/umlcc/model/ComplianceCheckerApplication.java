package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Facade for the application.
 * @author Joe Hardy
 */
public class ComplianceCheckerApplication {
    private static ComplianceCheckerApplication application;
    private ComplianceChecker complianceChecker;
    private Config config;
    private User user;

    /**
     * Constructor for the ComplianceCheckerApplication class.
     */
    private ComplianceCheckerApplication() {
        complianceChecker = ComplianceChecker.getInstance();
        config = Config.getInstance();
    }

    /**
     * Get the static application instance of this session, or make one if
     * there isn't one already.
     * @return the ComplianceCheckerApplication instance.
     */
    public ComplianceCheckerApplication getInstance() {
        if (complianceChecker == null) application = new ComplianceCheckerApplication();
        return application;
    }

    /**
     * Load user from DataLoader to the user attribute.
     * @return true if loading was successful, false otherwise.
     */
    public boolean loadUser() {
        user = DataLoader.loadUser();
        return user != null;
    }

    /**
     * Write user to JSON using DataWriter,
     * @return true if writing was successful, false otherwise.
     */
    public boolean saveUser() {
        return DataWriter.saveUser(user);
    }

    /**
     * Set the UserType of the user.
     * @param userType the UserType to set to.
     * @return true if the UserType has changed, false otherwise.
     */
    public boolean setUserType(UserType userType) {
        return user.setUserType(userType);
    }

    /**
     * Accessor method for the user's type.
     * @return the user's type.
     */
    public UserType getUserType() {
        return user.getUserType();
    }

    public boolean generateUmlcc() {
        return false;
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

    public ArrayList<Directory> getSubDirs() {
        return null;
    }

    public ArrayList<UserFile> getFiles() {
        return null;
    }

    /**
     * Access a class from a file.
     * @param file the file to get a class from.
     * @param name the name of the class.
     * @return the class.
     */
    public JavaClass getClass(UserFile file, String name) {
        return file.getClass(name);
    }

    /**
     * Access a method from a class.
     * @param jClass the class to get a method from.
     * @param name the name of the method.
     * @return the method.
     */
    public JavaMethod getMethod(JavaClass jClass, String name) {
        return jClass.getMethod(name);
    }

    /**
     * Access the content of a method.
     * @param jMethod the method to get content from.
     * @return the content.
     */
    public String getContent(JavaMethod jMethod) {
        return jMethod.getContent();
    }

    /**
     * Get the comment of a class, method, or variable.
     * @param jThing the class, method, or variable to get a comment from
     * @return the comment.
     */
    public JavaDoc getComment(JavaThing jThing) {
        return jThing.getJavaDoc();
    }

    /**
     * Set the configuration data.
     * @param warningMessages the warning messages for the config file.
     * @return true if changed, false otherwise.
     */
    public boolean setConfig(HashMap<Warning, String> warningMessages) {
        return false;
    }

    /**
     * Close the application.
     */
    public void close() {
        System.exit(0);
    }
}
