package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

public class JavaClass {
    private HashMap<String, JavaVariable> variables;
    private HashMap<String, JavaMethod> methods;
    private boolean isInterface;

    public JavaClass(String name, ArrayList<Modifier> modifiers, String comment,
                     ArrayList<JavaVariable> variables, ArrayList<JavaMethod> methods) {

    }

    public JavaVariable getVariable(String name) {
        return null;
    }

    public JavaMethod getMethod(String name) {
        return null;
    }

    public boolean isInterface() {
        return false;
    }
}
