package com.umlcc.model;

public enum Warning {
    NONE,
    NO_JAVADOC,
    NO_JAVADOC_CLASS,
    NO_JAVADOC_METHOD,
    NO_JAVADOC_PARAMETER,
    NO_JAVADOC_RETURN,
    NO_JAVADOC_AUTHOR,
    INCORRECT_PRIVACY_ATTRIBUTE,
    INCORRECT_PRIVACY_METHOD,
    NOT_FOLLOWING_UML,
    EXTRA_CLASS_ATTRIBUTE,
    EXTRA_NON_PRIVATE_METHOD,
    MISSING_FILE,
    EXTRA_FILE,
    INDENTATION_ISSUES,
    SPACING_ISSUES;

    /**
     * Input a String, output a UserType matching that String.
     * @param s the String to match.
     * @return the UserType that matches.
     */
    public static Warning fromString(String s) {
        for (Warning warning : values()) {
            if (warning.name().equalsIgnoreCase(s)) {
                return warning;
            }
        }
        return null;
    }
}