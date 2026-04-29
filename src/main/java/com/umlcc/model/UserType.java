package com.umlcc.model;

public enum UserType {
    BASIC,
    GRADER,
    ADMIN;

    /**
     * Input a String, output a UserType matching that String.
     * @param s the String to match.
     * @return the UserType that matches.
     */
    public static UserType fromString(String s) {
        for (UserType type : values()) {
            if (type.name().equalsIgnoreCase(s)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String s = super.toString();
        s = s.substring(0,1) + s.substring(1).toLowerCase();
        return s;
    }
}