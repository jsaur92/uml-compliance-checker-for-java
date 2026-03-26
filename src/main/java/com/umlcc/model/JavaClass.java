package com.umlcc.model;

import java.util.ArrayList;

/**
 * A single Java class.
 * @author Joe Hardy
 */
public class JavaClass extends JavaThing {
    private ArrayList<JavaVariable> variables;
    private ArrayList<JavaMethod> methods;
    private ArrayList<String> inheritedNames;
    private String implementedName;

    /**
     * Constructor for the JavaClass class.
     * @param name the name of this class.
     * @param modifiers all modifiers for this class including privacy modifiers.
     * @param comment the comment placed above this class.
     * @param variables the variables of this class.
     * @param methods the methods of this class
     * @param inheritedNames the classes this class inherits from.
     * @param implementedName the class this class implements.
     */
    public JavaClass(String name, ArrayList<Modifier> modifiers, String comment,
                     ArrayList<JavaVariable> variables, ArrayList<JavaMethod> methods,
                     ArrayList<String> inheritedNames, String implementedName) {
        super(name, modifiers, comment);
        this.variables = variables;
        this.methods = methods;
        this.inheritedNames = inheritedNames;
        this.implementedName = implementedName;
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
     * Accesses the inherited class' names.
     * @return the names of the classes this class inherits.
     */
    public ArrayList<String> getInheritedNames() {
        return inheritedNames;
    }

    /**
     * Modifies the inherited class' names.
     * @param inheritedNames the names of the classes this class inherits.
     */
    public void setInheritedNames(ArrayList<String> inheritedNames) {
        this.inheritedNames = inheritedNames;
    }

    /**
     * Accesses the implementedName String.
     * @return the implementedName String.
     */
    public String getImplementedName() {
        return implementedName;
    }

    /**
     * Modifies the implementedName String.
     * @param implementedName the name of the class to implement.
     */
    public void setImplementedName(String implementedName) {
        this.implementedName = implementedName;
    }

    /**
     * Checks if this class inherits any classes.
     * @return true if this class inherits one or more classes, false otherwise.
     */
    public boolean inheritsClasses() {
        return !inheritedNames.isEmpty();
    }

    /**
     * Checks if this class implements any interfaces.
     * @return true if this class implements an interface, false otherwise.
     */
    public boolean isInterface() {
        return implementedName != null;
    }

    @Override
    public String toString() {
        String header = "";
        for (Modifier modifier : getModifiers()) {
            header += modifier.toString() + " ";
        }
        header += (isInterface() ? "interface " : "class ");
        header += getName();
        if (inheritsClasses()) {
            header += " extends " + inheritedNames.getFirst();
            for (int i = 1; i < inheritedNames.size(); i++) {
                header += ", " + inheritedNames.get(i);
            }
        }
        if (isInterface()) {
            header += " implements " + implementedName;
        }
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
