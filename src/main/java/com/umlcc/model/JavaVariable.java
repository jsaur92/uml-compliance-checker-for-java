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
        return null;
    }

    @Override
    public ArrayList<EvaluationResult> checkCompliance(JavaThing other) {
        return null;
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
}
