package com.umlcc.model;

public enum CloneIntoPattern {
    NONE,
    PROJECT_NAME,
    USER_NAME,
    INDEX;

    /**
     * Input a String, output a CloneIntoPattern matching that String.
     * @param s the String to match.
     * @return the CloneIntoPattern that matches.
     */
    public static CloneIntoPattern fromString(String s) {
        s = s.toLowerCase();
        if (s.length() <= 1) return NONE;
        if (s.startsWith("/")) s = s.substring(1);
        return switch (s.charAt(0)) {
            case 'p' -> PROJECT_NAME;
            case 'u' -> USER_NAME;
            case 'i' -> INDEX;
            default -> NONE;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case PROJECT_NAME -> "/project-name/";
            case USER_NAME -> "/user-name/";
            case INDEX -> "/index/";
            default -> "/";
        };
    }
}
