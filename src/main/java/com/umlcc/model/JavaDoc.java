package com.umlcc.model;

import java.util.ArrayList;

public class JavaDoc {
    private ArrayList<String> lines;
    private ArrayList<String> blockTags;

    public JavaDoc(String rawlines) {

    }

    public static boolean isJavaDoc(String rawlines) {
        return false;
    }

    public String getMessage() {
        return null;
    }

    public ArrayList<String> getBlockTags() {
        return blockTags;
    }

    public ArrayList<EvaluationResult> checkCompliance(JavaThing thing) {
        return null;
    }

    public ArrayList<EvaluationResult> checkCompliance(JavaThing thing, JavaThing other) {
        return null;
    }
}
