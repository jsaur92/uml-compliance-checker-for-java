package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A single Java class.
 * @author Joe Hardy
 */
public class JavaClass extends JavaThing {
    private ArrayList<JavaVariable> variables;
    private ArrayList<JavaMethod> methods;
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
        this.variables = variables;
        this.methods = methods;
        this.isInterface = isInterface;
    }

    @Override
    public ArrayList<EvaluationResult> checkCompliance() {
        ArrayList<EvaluationResult> results = new ArrayList<EvaluationResult>();

        if (config.hasWarning(Warning.NO_JAVADOC_CLASS)) {
            if (!hasJavaDoc()) results.add(
                    new EvaluationResult(Warning.NO_JAVADOC_CLASS, this)
            );
        }
        else if (config.hasWarning(Warning.NO_JAVADOC_AUTHOR)) {
            if (getJavaDoc().getBlockTags().contains("@author")) results.add(
                    new EvaluationResult(Warning.NO_JAVADOC_AUTHOR, this)
            );
        }

        for (JavaVariable var : variables)
            results.addAll(var.checkCompliance());
        for (JavaMethod method : methods)
            results.addAll(method.checkCompliance());

        return results;
    }

    @Override
    public ArrayList<EvaluationResult> checkCompliance(JavaThing other) {
        JavaClass otherClass = (JavaClass) other;
        ArrayList<EvaluationResult> results = new ArrayList<EvaluationResult>(checkCompliance());

        if (config.hasWarning(Warning.NOT_FOLLOWING_UML)) {
            for (JavaVariable var : otherClass.getVariables()) {
                if (!hasVariable(var.getName())) results.add(
                        new EvaluationResult(Warning.NOT_FOLLOWING_UML, this, other)
                );
            }
            for (JavaMethod method : otherClass.getMethods()) {
                if (!hasMethod(method.getName())) results.add(
                        new EvaluationResult(Warning.NOT_FOLLOWING_UML, this, other)
                );
            }
        }

        if (config.hasWarning(Warning.EXTRA_CLASS_ATTRIBUTE)) {
            for (JavaVariable var : variables) {
                if (!otherClass.hasVariable(var.getName())) results.add(
                        new EvaluationResult(Warning.EXTRA_CLASS_ATTRIBUTE, this, other)
                );
            }
        }

        if (config.hasWarning(Warning.EXTRA_NON_PRIVATE_METHOD)) {
            for (JavaMethod method : methods) {
                if (!otherClass.hasMethod(method.getName())) results.add(
                        new EvaluationResult(Warning.EXTRA_CLASS_ATTRIBUTE, this, other)
                );
            }
        }

        for (JavaVariable var : variables)
            results.addAll(var.checkCompliance(otherClass.getVariable(var.getName())));
        for (JavaMethod method : methods)
            results.addAll(method.checkCompliance(otherClass.getMethod(method.getName())));

        return results;
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
     * Accessor method for the variables ArrayList.
     * @return the variables ArrayList.
     */
    public ArrayList<JavaVariable> getVariables() {
        return variables;
    }

    /**
     * Accessor method for the methods ArrayList.
     * @return the methods ArrayList.
     */
    public ArrayList<JavaMethod> getMethods() {
        return methods;
    }

    /**
     * Checks if this class has a variable of a given name.
     * @param name the name of the variable to search for.
     * @return true if this class has the given variable, false otherwise.
     */
    public boolean hasVariable(String name) {
        for (JavaVariable var : variables) {
            if (var.getName().equals(name)) return true;
        }
        return false;
    }

    /**
     * Checks if this class has a method of a given name/
     * @param name the name of the method to search for.
     * @return true if this class has the given method, false otherwise.
     */
    public boolean hasMethod(String name) {
        for (JavaMethod method : methods) {
            if (method.getName().equals(name)) return true;
        }
        return false;
    }

    /**
     * Access a single variable from this class's variables.
     * @param name the name of the variable to access.
     * @return the variable to access.
     */
    public JavaVariable getVariable(String name) {
        for (JavaVariable var : variables) {
            if (var.getName().equals(name)) return var;
        }
        return null;
    }

    /**
     * Access a single variable from this class's variables.
     * @param name the name of the variable to access.
     * @return the variable to access.
     */
    public JavaMethod getMethod(String name) {
        for (JavaMethod method : methods) {
            if (method.getName().equals(name)) return method;
        }
        return null;
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
        return isInterface;
    }

    @Override
    public String toString() {
        String header = "";
        for (Modifier modifier : getModifiers()) {
            header += modifier.toString() + " ";
        }
        header += (isInterface() ? "interface " : "class ");
        header += getName();
        // add in logic for extends and implements.
        String body = "";
        for (JavaVariable var : getVariables()) {
            body += "\n" + var;
        }
        for (JavaMethod method : getMethods()) {
            body += "\n" + method;
        }
        body = body.replaceAll("\n", "\n\t");   //indent the body

        return header + body;
    }
}
