package com.umlcc.model;

public class User {
    private UserType userType;
    private String lastUmlDataPath;
    private String lastMyCodePath;

    public User(UserType userType, String lastUmlDataPath, String lastMyCodePath) {
        this.userType = userType;
        this.lastUmlDataPath = lastUmlDataPath;
        this.lastMyCodePath = lastMyCodePath;
    }
}
