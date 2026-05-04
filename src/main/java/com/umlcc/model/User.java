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
    private String defaultCloneParent;
    private boolean deleteClonedOnClose;
    private CloneIntoPattern cloneIntoPattern;

    /**
     * Constructor for a new User class.
     * @param userType the type of user this user is (basic, grader, admin).
     */
    public User(UserType userType) {
        this.userType = userType;
        this.lastUmlDataPath = "";
        this.lastMyCodePath = "";
        this.defaultCloneParent = "";
        this.deleteClonedOnClose = false;
        this.cloneIntoPattern = CloneIntoPattern.NONE;
    }

    /**
     * Constructor for an existing User class.
     * @param userType the type of user this user is (basic, grader, admin).
     * @param lastUmlDataPath the filepath for last session's UML Data path.
     * @param lastMyCodePath the filepath for last session's My Code path.
     */
    public User(UserType userType, String lastUmlDataPath,
                String lastMyCodePath, String defaultCloneParent,
                boolean deleteClonedOnClose, CloneIntoPattern cloneIntoPattern) {
        this.userType = userType;
        this.lastUmlDataPath = lastUmlDataPath;
        this.lastMyCodePath = lastMyCodePath;
        this.defaultCloneParent = defaultCloneParent;
        this.deleteClonedOnClose = deleteClonedOnClose;
        this.cloneIntoPattern = cloneIntoPattern;
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
     * Accessor method for the user's default directory for cloning repos into.
     * @return the user's default clone-into directory filepath.
     */
    public String getDefaultCloneParent() {
        return defaultCloneParent;
    }

    /**
     * Accessor method for whether the user wants to delete all cloned files
     * on close or not.
     * @return true if the user wants their cloned files deleted on close,
     * false otherwise.
     */
    public boolean getDeleteClonedOnClose() {
        return deleteClonedOnClose;
    }

    /**
     * Accessor method for the user's CloneIntoPattern.
     * @return the user's CloneIntoPattern.
     */
    public CloneIntoPattern getCloneIntoPattern() {
        return cloneIntoPattern;
    }

    /**
     * Mutator method for the user's last UML Data filepath.
     */
    public void setLastUmlDataPath(String lastUmlDataPath) {
        this.lastUmlDataPath = lastUmlDataPath;
    }

    /**
     * Mutator method for the user's last My Code filepath.
     */
    public void setLastMyCodePath(String lastMyCodePath) {
        this.lastMyCodePath = lastMyCodePath;
    }

    /**
     * Mutator method for the user's default repo to clone projects into's
     * filepath.
     */
    public void setDefaultCloneParent(String defaultCloneParent) {
        this.defaultCloneParent = defaultCloneParent;
    }

    /**
     * Mutator method for the user's preference for file deletion on close.
     */
    public void setDeleteClonedOnClose(boolean deleteClonedOnClose) {
        this.deleteClonedOnClose = deleteClonedOnClose;
    }

    /**
     * Mutator method for the user's CloneIntoPattern.
     * @param cloneIntoPattern the user's CloneIntoPattern.
     */
    public void setCloneIntoPattern(CloneIntoPattern cloneIntoPattern) {
        this.cloneIntoPattern = cloneIntoPattern;
    }
}
