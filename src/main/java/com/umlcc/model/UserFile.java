package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

public class UserFile {
    private HashMap<String, JavaClass> classes;
    private ModificationRule modRule;

    public UserFile(ArrayList<JavaClass> classes, ModificationRule modRule) {

    }

    public JavaClass getClass(String name) {
        return null;
    }

    public ArrayList<EvaluationResult> checkCompliance() {
        return null;
    }

    public ArrayList<EvaluationResult> checkCompliance(UserFile other) {
        return null;
    }
}
