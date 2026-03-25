package com.umlcc.model;

public enum Modifier {
    DEFAULT,
    PRIVATE,
    PROTECTED,
    PUBLIC,
    FINAL,
    STATIC,
    ABSTRACT,
    TRANSIENT,
    SYNCHRONIZED,
    VOLATILE;

    /**
     * Input a String, output a Modifier matching that String.
     * @param s the String to match.
     * @return the Modifier that matches.
     */
    public static Modifier fromString(String s) {
        for (Modifier mod : values()) {
            if (mod.name().equalsIgnoreCase(s)) {
                return mod;
            }
        }
        return null;
    }

    /**
     * Determine whether a String is the same as an existing Modifier.
     * @param s the String to check.
     * @return true if that String is the same as a Modifier, false otherwise.
     */
    public static boolean hasValue(String s) {
        for (Modifier mod : values()) {
            if (mod.name().equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }
}