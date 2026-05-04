package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A single method of a Java class.
 * @author Joe Hardy
 */
public class JavaMethod extends JavaThing {
    private ArrayList<JavaVariable> parameters;
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
        this.parameters = parameters;
        this.returnType = returnType;
        this.content = content;
    }

    @Override
    public ArrayList<EvaluationResult> checkCompliance() {
        ArrayList<EvaluationResult> results = new ArrayList<EvaluationResult>();

        if (hasJavaDoc()) {
            //this checks to see if it has @param at all, but it should check to see if there is @param for every parameter.
            if (!parameters.isEmpty() && getJavaDoc().getBlockTags().contains("@param")) {
                tryAddWarning(results, Warning.NO_JAVADOC_PARAMETER);
            }

            if (!(returnType == null || returnType.equals("void")) && getJavaDoc().getBlockTags().contains("@return")) {
                tryAddWarning(results, Warning.NO_JAVADOC_RETURN);
            }
        } else {
            tryAddWarning(results, Warning.NO_JAVADOC);
        }

        return results;
    }

    @Override
    public ArrayList<EvaluationResult> checkCompliance(JavaThing other) {
        JavaMethod otherMethod = (JavaMethod) other;
        ArrayList<EvaluationResult> results = new ArrayList<EvaluationResult>(checkCompliance());

        if (otherMethod == null) {
            if (!getModifiers().contains(Modifier.PUBLIC)) {
                tryAddWarning(results, Warning.EXTRA_NON_PRIVATE_METHOD);
            }
        }
        else {
            for (JavaVariable par : getParameters()) {
                if (!otherMethod.hasParameter(par.getName()))
                    tryAddWarning(results, Warning.NOT_FOLLOWING_UML, other);
            }

            for (JavaVariable par : otherMethod.getParameters()) {
                if (!hasParameter(par.getName()))
                    tryAddWarning(results, Warning.NOT_FOLLOWING_UML, other);
            }

            // this actually checks all modifiers right now, so it should be tuned to be more specific in the future.
            if (!modifiers.equals(otherMethod.getModifiers())) {
                tryAddWarning(results, Warning.INCORRECT_PRIVACY_METHOD, other);
            }
        }

        return results;
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
     * Checks to see if a parameter of a given name exists in parameters.
     * @param name the name of the parameter
     * @return true if the parameter is in parameters, false otherwise.
     */
    public boolean hasParameter(String name) {
        for (JavaVariable par : parameters) {
            if (par.getName().equals(name)) return true;
        }
        return false;
    }

    /**
     * Accessor method for parameters.
     * @return the parameters of this method.
     */
    public ArrayList<JavaVariable> getParameters() {
        return parameters;
    }

    /**
     * Access a single parameter.
     * @param name the name of the parameter to access.
     * @return the parameter to access.
     */
    public JavaVariable getParameter(String name) {
        for (JavaVariable par : parameters) {
            if (par.getName().equals(name)) return par;
        }
        return null;
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

    /**
     * Mutator method for the content.
     * @param content the code to put inside this method.
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        String s = "";
        for (Modifier mod : getModifiers()) s += mod + " ";
        if (getReturnType() != null) s += getReturnType() + " ";
        s += getName() + "(";
        for (JavaVariable var : getParameters()) {
            s += var + ", ";
        }
        if (s.endsWith(", ")) s = s.substring(0, s.length()-2);
        s += ")";
        return s;
    }
}
