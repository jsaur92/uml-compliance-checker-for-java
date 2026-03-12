package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

public class JavaMethod {
    private HashMap<String, JavaVariable> parameters;
    private String returnType;
    private String content;

    public JavaMethod(String name, ArrayList<Modifier> modifiers, String comment,
                     ArrayList<JavaVariable> parameters, String returnType, String content) {

    }

    public HashMap<String, JavaVariable> getParameters() {
        return null;
    }

    public JavaVariable getParameter(String name) {
        return null;
    }

    public String getReturnType() {
        return null;
    }

    public String getContent() {
        return null;
    }
}
