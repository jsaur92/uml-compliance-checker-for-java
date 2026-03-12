package com.umlcc.model;

import java.util.ArrayList;

public abstract class JavaThing {
    private String name;
    private ArrayList<Modifier> modifiers;
    private JavaDoc comment;
    private Config config;

    public JavaThing(String name, ArrayList<Modifier> modifiers, String comment) {

    }

    public abstract ArrayList<EvaluationResult> checkCompliance();
    public abstract ArrayList<EvaluationResult> checkCompliance(JavaThing other);
    public abstract boolean equals(JavaThing other);

    public boolean hasJavaDoc() {
        return false;
    }
}
