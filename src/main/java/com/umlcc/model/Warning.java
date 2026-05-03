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
            if (warning.name().equalsIgnoreCase(s) || warning.toString().equalsIgnoreCase(s)) {
                return warning;
            }
        }
        return null;
    }

    /**
     * Used to directly get a String representation of the Warning.
     * @return the name of the Warning.
     */
    public String getName() {
        return this.name();
    }

    /**
     * Used to get a user-facing description of what the Warning covers.
     * @return the text representing the Warning that would make sense to a user.
     */
    public String toString() {
        return switch (this) {
            case NO_JAVADOC -> "No Javadoc";
            case NO_JAVADOC_CLASS -> "No Javadoc in a class";
            case NO_JAVADOC_METHOD -> "No Javadoc in a method";
            case NO_JAVADOC_PARAMETER -> "No @param in a method Javadoc";
            case NO_JAVADOC_RETURN -> "No @return in a method Javadoc";
            case NO_JAVADOC_AUTHOR -> "No @author in a class Javadoc";
            case INCORRECT_PRIVACY_ATTRIBUTE -> "Incorrect privacy for an attribute";
            case INCORRECT_PRIVACY_METHOD -> "Incorrect privacy for a method";
            case NOT_FOLLOWING_UML -> "Not following the UML (generic)";
            case EXTRA_CLASS_ATTRIBUTE -> "Extra attribute in a class";
            case EXTRA_NON_PRIVATE_METHOD -> "Extra non-private method in a class";
            case MISSING_FILE -> "Missing required file";
            case EXTRA_FILE -> "Included extra file";
            case INDENTATION_ISSUES -> "Indentation issues";
            case SPACING_ISSUES -> "Spacing issues";
            default -> "None";
        };
    }
}