package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A single method of a Java class.
 * @author Joe Hardy
 */
public class JavaMethod extends JavaThing {
    private HashMap<String, JavaVariable> parameters;
    private String returnType;
    private String content;

    /**
     * Constructor for the JavaMethod class.
     * @param name the name of this method.
     * @param modifiers all modifiers for this method including privacy modifiers.
     * @param comment the comment placed above this method.
     * @param parameters the parameters for this method.
     * @param returnType the return type of this method.
     * @param content the code inside of this method.
     */
    public JavaMethod(String name, ArrayList<Modifier> modifiers, String comment,
                     ArrayList<JavaVariable> parameters, String returnType, String content) {
        super(name, modifiers, comment);
        this.parameters = new HashMap<String, JavaVariable>();
        for (JavaVariable var : parameters) {
            this.parameters.put(var.getName(), var);
        }
        this.returnType = returnType;
        this.content = content;
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
        JavaMethod otherMethod = (JavaMethod) other;
        if (!parameters.equals(otherMethod.getParameters())) return false;
        if (!returnType.equals(otherMethod.getReturnType())) return false;
        // should the content have to be equal for the methods to be equal?
        return true;
    }

    /**
     * Accessor method for parameters.
     * @return the parameters of this method.
     */
    public HashMap<String, JavaVariable> getParameters() {
        return parameters;
    }

    /**
     * Access a single parameter.
     * @param name the name of the parameter to access.
     * @return the parameter to access.
     */
    public JavaVariable getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * Accessor method for the return type.
     * @return the return type of this method.
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Accessor method for the content.
     * @return the code inside of this method.
     */
    public String getContent() {
        return content;
    }
}
