package com.umlcc.model;

/**
 * Shared data and functionality of DataLoader and DataWriter.
 * @author Joe Hardy
 */
public abstract class DataConstants {
    protected final static String JSON_DIR_PATH = "json/";
    protected final static String USER_FILE_NAME = "user.json";
    protected final static String USER_TYPE = "userType";
    protected final static String USER_LAST_UML = "lastUmlDataPath";
    protected final static String USER_LAST_MY_CODE = "lastMyCodePath";
    protected final static String CONFIG_FILE_NAME = "config.json";
    protected final static String CONFIG_WARNINGS = "warnings";
    protected final static String CONFIG_WARNINGS_WARNING = "warning";
    protected final static String CONFIG_WARNINGS_MESSAGE = "message";

    protected final static String UMLCC_DIR_PATH = "umlcc/";
    protected final static String UMLCC_FILE_EXTENSION = ".umlcc";
    protected final static String REGEX_FILE_START = "\\{";
    protected final static String REGEX_FILE_END = "\\}";
    protected final static String DELIMITER_FILE_START = "{";
    protected final static String DELIMITER_FILE_END = "}";
    protected final static String DELIMITER_METHOD_START = "(";
    protected final static String DELIMITER_METHOD_END = ")";
    protected final static String DELIMITER_TAB = "\t";
    protected final static String DELIMITER_NEWLINE = "\n";
    protected final static String DELIMITER_SPACE = " ";
    protected final static String DELIMITER_DOT = ".";
    protected final static String DELIMITER_COMMA = ",";
    protected final static String DELIMITER_SLASH = "/";
    protected final static String DELIMITER_SEMICOLON = ";";

    protected final static String CLASS_DESIGNATION = "class";
    protected final static String INTERFACE_DESIGNATION = "interface";
    protected final static String EXTENDS_DESIGNATION = "extends";
    protected final static String IMPLEMENTS_DESIGNATION = "implements";
    protected final static String ENUM_DESIGNATION = "enum";

    /**
     * Make a copy of a String and remove all whitespace characters.
     * @param s the String to operate on.
     * @return a copy of s without any whitespace.
     */
    protected static String removeWhitespace(String s) {
        return s.replaceAll("\\s", "");
    }

    /**
     * Count the number of tabs in a line.
     * @param line the line to count tabs of.
     * @return the number of tabs in the given line.
     */
    protected static int countTabs(String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == DELIMITER_TAB.charAt(0)) count++;
        }
        return count;
    }
}
