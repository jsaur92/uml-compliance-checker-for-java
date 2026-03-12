package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

public class ComplianceCheckerApplication {
    private static ComplianceCheckerApplication application;
    private ComplianceChecker complianceChecker;
    private Config config;
    private User user;

    private ComplianceCheckerApplication() {

    }

    public ComplianceCheckerApplication getInstance() {
        return null;
    }

    public boolean LoadUser() {
        return false;
    }

    public boolean SaveUser() {
        return false;
    }

    public boolean setUserType(UserType userType) {
        return false;
    }

    public UserType getUserType() {
        return null;
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

    public JavaClass getClass(UserFile file, String name) {
        return null;
    }

    public JavaMethod getMethod(JavaClass jClass, String name) {
        return null;
    }

    public String getContent(JavaMethod jMethod, String name) {
        return null;
    }

    public JavaDoc getComment(JavaThing jThing) {
        return null;
    }

    public boolean setConfig(HashMap<Warning, String> warningMessages) {
        return false;
    }

    public boolean close() {
        return false;
    }
}
