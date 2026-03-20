package com.umlcc.model;

/**
 * Persistent user data for this application. The system does not expect to
 * have multiple users per device.
 * @author Joe Hardy
 */
public class User {
    private UserType userType;
    private String lastUmlDataPath;
    private String lastMyCodePath;

    /**
     * Constructor for a new User class.
     * @param userType the type of user this user is (basic, grader, admin).
     */
    public User(UserType userType) {
        this.userType = userType;
        this.lastUmlDataPath = "";
        this.lastMyCodePath = "";
    }

    /**
     * Constructor for an existing User class.
     * @param userType the type of user this user is (basic, grader, admin).
     * @param lastUmlDataPath the filepath for last session's UML Data path.
     * @param lastMyCodePath the filepath for last session's My Code path.
     */
    public User(UserType userType, String lastUmlDataPath, String lastMyCodePath) {
        this.userType = userType;
        this.lastUmlDataPath = lastUmlDataPath;
        this.lastMyCodePath = lastMyCodePath;
    }

    /**
     * Accessor method for the user's type.
     * @return the user's type.
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Mutator method for the user's type.
     * @param userType the UserType to change to.
     * @return true if the usertype has changed, false otherwise.
     */
    public boolean setUserType(UserType userType) {
        boolean changed = userType != this.userType;
        this.userType = userType;
        return changed;
    }

    /**
     * Accessor method for the user's last UML Data filepath.
     * @return the user's last UML Data filepath.
     */
    public String getLastUmlDataPath() {
        return lastUmlDataPath;
    }

    /**
     * Accessor method for the user's last My Code filepath.
     * @return the user's last My Code filepath.
     */
    public String getLastMyCodePath() {
        return lastMyCodePath;
    }

    /**
     * Mutator method for the user's last UML Data filepath.
     */
    public void setLastUmlDataPath(String lastUmlDataPath) {
        this.lastMyCodePath = lastUmlDataPath;
    }

    /**
     * Mutator method for the user's last My Code filepath.
     */
    public void setLastMyCodePath(String lastMyCodePath) {
        this.lastMyCodePath = lastMyCodePath;
    }
}
