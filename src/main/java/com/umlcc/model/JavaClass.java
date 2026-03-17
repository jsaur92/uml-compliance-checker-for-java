package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A single Java class.
 * @author Joe Hardy
 */
public class JavaClass extends JavaThing {
    private HashMap<String, JavaVariable> variables;
    private HashMap<String, JavaMethod> methods;
    private boolean isInterface;

    /**
     * Constructor for the JavaClass class.
     * @param name the name of this class.
     * @param modifiers all modifiers for this class including privacy modifiers.
     * @param comment the comment placed above this class.
     * @param variables the variables of this class.
     * @param methods the methods of this class
     * @param isInterface true if this "class" is actually an interface.
     */
    public JavaClass(String name, ArrayList<Modifier> modifiers, String comment,
                     ArrayList<JavaVariable> variables, ArrayList<JavaMethod> methods,
                     boolean isInterface) {
        super(name, modifiers, comment);
        this.variables = new HashMap<String, JavaVariable>();
        for (JavaVariable var : variables) {
            this.variables.put(var.getName(), var);
        }
        this.methods = new HashMap<String, JavaMethod>();
        for (JavaMethod method : methods) {
            this.methods.put(method.getName(), method);
        }
        this.isInterface = isInterface;
    }

    @Override
    public ArrayList<EvaluationResult> checkCompliance() {
        return null;
    }

    @Override
    public ArrayList<EvaluationResult> checkCompliance(JavaThing other) {
        return null;
    }

    @Override
    public boolean equals(JavaThing other) {
        if (!super.equals(other)) return false;
        JavaClass otherClass = (JavaClass) other;
        if (isInterface() != otherClass.isInterface()) return false;
        if (!variables.equals(otherClass.getVariables())) return false;
        if (!methods.equals(otherClass.getMethods())) return false;
        return true;
    }

    /**
     * Accessor method for the variables HashMap.
     * @return the variables HashMap.
     */
    public HashMap<String, JavaVariable> getVariables() {
        return variables;
    }

    /**
     * Accessor method for the methods HashMap.
     * @return the methods HashMap.
     */
    public HashMap<String, JavaMethod> getMethods() {
        return methods;
    }

    /**
     * Access a single variable from this class's variables.
     * @param name the name of the variable to access.
     * @return the variable to access.
     */
    public JavaVariable getVariable(String name) {
        return variables.get(name);
    }

    /**
     * Access a single variable from this class's variables.
     * @param name the name of the variable to access.
     * @return the variable to access.
     */
    public JavaMethod getMethod(String name) {
        return methods.get(name);
    }

    /**
     * Get the number of variables in this class.
     * @return the number of variables in this class.
     */
    public int getVariablesCount() {
        return variables.size();
    }

    /**
     * Get the number of methods in this class.
     * @return the number of methods in this class.
     */
    public int getMethodsCount() {
        return methods.size();
    }

    /**
     * Determines if this class is actually an interface or not.
     * @return true if this is an interface, false otherwise.
     */
    public boolean isInterface() {
        return false;
    }
}
