package com.umlcc.model;

import java.util.ArrayList;

/**
 * A single variable inside a Java class. Can be an instance variable or a
 * parameter.
 * @author Joe Hardy
 */
public class JavaVariable extends JavaThing {
    private String type;

    /**
     * Constructor for the JavaVariable class.
     * @param name the name of this variable.
     * @param modifiers all modifiers for this variable including privacy modifiers.
     * @param comment the comment placed above this variable.
     * @param type the type of this variable.
     */
    public JavaVariable(String name, ArrayList<Modifier> modifiers,
                        String comment, String type) {
        super(name, modifiers, comment);
        this.type = type;
    }

    @Override
    public ArrayList<EvaluationResult> checkCompliance() {
        ArrayList<EvaluationResult> results = new ArrayList<EvaluationResult>();

        return results;
    }

    @Override
    public ArrayList<EvaluationResult> checkCompliance(JavaThing other) {
        JavaVariable otherVar = (JavaVariable) other;
        ArrayList<EvaluationResult> results = new ArrayList<EvaluationResult>(checkCompliance());

        if (other == null) {
            if (config.hasWarning(Warning.EXTRA_CLASS_ATTRIBUTE)) results.add(
                    new EvaluationResult(Warning.EXTRA_CLASS_ATTRIBUTE, this, other)
            );
        }
        else {
            // this actually checks all modifiers right now, so it should be tuned to be more specific in the future.
            if (config.hasWarning(Warning.INCORRECT_PRIVACY_METHOD)) {
                if (!modifiers.equals(otherVar.getModifiers())) results.add(
                        new EvaluationResult(Warning.INCORRECT_PRIVACY_METHOD, this, other)
                );
            }

            if (config.hasWarning(Warning.NOT_FOLLOWING_UML)) {
                if (!type.equals(otherVar.getType())) results.add(
                        new EvaluationResult(Warning.NOT_FOLLOWING_UML, this, other)
                );
            }
        }
        return results;
    }

    @Override
    public boolean equals(JavaThing other) {
        if (!super.equals(other)) return false;
        JavaVariable otherVar = (JavaVariable) other;
        if (!type.equals(otherVar.getType())) return false;
        return true;
    }

    /**
     * Accessor method for type.
     * @return this variable's type.
     */
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        String s = "";
        for (Modifier mod : getModifiers()) s += mod + " ";
        s += getType() + " " + getName();
        return s;
    }
}
