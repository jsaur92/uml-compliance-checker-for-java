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
        user = loadUser();
    }

    /**
     * Get the static application instance of this session, or make one if
     * there isn't one already.
     * @return the ComplianceCheckerApplication instance.
     */
    public static ComplianceCheckerApplication getInstance() {
        if (application == null) application = new ComplianceCheckerApplication();
        return application;
    }

    /**
     * Load user from DataLoader to the user attribute.
     * @return the loaded user data.
     */
    public User loadUser() {
        return DataLoader.loadUser();
    }

    /**
     * Write user to JSON using DataWriter,
     * @return true if writing was successful, false otherwise.
     */
    public boolean saveUser() {
        return DataWriter.saveUser(user);
    }

    /**
     * Attempt to pull or clone a Git repo.
     * @param url the url to the remote Git repo.
     * @param parentDirPath the path to the directory to clone/pull into.
     * @param outputText a StringBuilder representing the output of the commands.
     * @return true if successfully cloned or pulled, false otherwise.
     */
    public boolean attemptPullGitRepo(String url, String parentDirPath, StringBuffer outputText) {
        return DataWriter.attemptPullGitRepo(url, parentDirPath, outputText);
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

    /**
     * Accessor method for the user's last UML Data filepath.
     * @return the user's last UML Data filepath.
     */
    public String getLastUmlDataPath() {
        return user.getLastUmlDataPath();
    }

    /**
     * Accessor method for the user's last My Code filepath.
     * @return the user's last My Code filepath.
     */
    public String getLastMyCodePath() {
        return user.getLastMyCodePath();
    }

    /**
     * Accessor method for the user's default directory for cloning repos into.
     * @return the user's default clone-into directory filepath.
     */
    public String getDefaultCloneParent() {
        return user.getDefaultCloneParent();
    }

    /**
     * Accessor method for whether the user wants to delete all cloned files
     * on close or not.
     * @return true if the user wants their cloned files deleted on close,
     * false otherwise.
     */
    public boolean getDeleteClonedOnClose() {
        return user.getDeleteClonedOnClose();
    }

    /**
     * Accessor method for the user's CloneIntoPattern.
     * @return the user's CloneIntoPattern.
     */
    public CloneIntoPattern getCloneIntoPattern() {
        return user.getCloneIntoPattern();
    }

    /**
     * Mutator method for the user's last UML Data filepath.
     */
    public void setLastUmlDataPath(String lastUmlDataPath) {
        user.setLastUmlDataPath(lastUmlDataPath);
    }

    /**
     * Mutator method for the user's last My Code filepath.
     */
    public void setLastMyCodePath(String lastMyCodePath) {
        user.setLastMyCodePath(lastMyCodePath);
    }

    /**
     * Mutator method for the user's default repo to clone projects into's
     * filepath.
     */
    public void setDefaultCloneParent(String defaultCloneParent) {
        user.setDefaultCloneParent(defaultCloneParent);
    }

    /**
     * Mutator method for the user's preference for file deletion on close.
     */
    public void setDeleteClonedOnClose(boolean deleteClonedOnClose) {
        user.setDeleteClonedOnClose(deleteClonedOnClose);
    }

    /**
     * Mutator method for the user's CloneIntoPattern.
     * @param cloneIntoPattern the user's CloneIntoPattern.
     */
    public void setCloneIntoPattern(CloneIntoPattern cloneIntoPattern) {
        user.setCloneIntoPattern(cloneIntoPattern);
    }

    /**
     * Save a directory as a .umlcc file to the umlcc directory.
     * @param dir the directory to save.
     * @return true if saving successful, false otherwise.
     */
    public boolean saveAsUmlccFile(Directory dir) {
        return DataWriter.saveAsUmlccFile(dir);
    }

    /**
     * Load a Directory containing all the data from a real Java project repository.
     * @param rootname the root of the file
     * @return the Directory.
     */
    public Directory loadRepo(String rootname) {
        return DataLoader.loadRepo(rootname);
    }

    /**
     * Load a Directory containing all the data from a real Java project
     * repository into the ComplianceChecker instance.
     * @param rootname the root of the file
     * @return true if successful.
     */
    public boolean loadUmlDataByRepo(String rootname) {
        return complianceChecker.loadUmlDataByRepo(rootname);
    }

    /**
     * Load a Directory containing all the data from a .umlcc file.
     * @param pathname path to the .umlcc file.
     * @return true if successful.
     */
    public boolean loadUmlDataByUmlcc(String pathname) {
        return complianceChecker.loadUmlDataByUmlcc(pathname);
    }

    /**
     * Accessor method for path.
     * @return the absolute path on the device's drive to the file or repo
     * used as the template for this ComplianceChecker.
     */
    public String getUmlDataPath() {
        return complianceChecker.getPath();
    }

    /**
     * Reset the current umlData to be null.
     * @return true if successful.
     */
    public boolean clearUmlData() {
        return complianceChecker.clearUmlData();
    }

    /**
     * Check the UML compliance of a Java project. Compares against umlData if
     * umlData is not null.
     * @param rootname the Java project to check.
     * @return the output that shows every error/warning for the project.
     */
    public ArrayList<String> checkCompliance(String rootname) {
        return complianceChecker.checkCompliance(rootname);
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
     * Accessor method for config.
     * @return the config instance.
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Set the configuration data.
     * @param warningMessages the warning messages for the config file.
     * @return true if changed, false otherwise.
     */
    public boolean setConfig(HashMap<Warning, String> warningMessages) {
        return config.setWarningMessages(warningMessages);
    }

    /**
     * Close the application.
     */
    public void close() {
        DataWriter.saveUser(user);
        DataWriter.saveConfigData(config.getWarningMessages());
        System.exit(0);
    }
}
