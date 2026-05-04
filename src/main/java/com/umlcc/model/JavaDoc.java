package com.umlcc.model;

import java.util.ArrayList;
import java.util.List;

public class JavaDoc {
    private ArrayList<String> lines;
    private ArrayList<String> blockTags;

    public JavaDoc(String rawlines) {
        lines = rawToLines(rawlines);
        blockTags = new ArrayList<String>();
        for (String line : lines) {
            for (String word : line.split(" ")) {
                if (word.length() > 0 && word.charAt(0) == '@') {
                    blockTags.add(word.substring(1));
                    break;
                }
            }
        }
    }

    public static boolean isJavaDoc(String rawlines) {
        ArrayList<String> lines = rawToLines(rawlines);
        return lines.getFirst().replaceAll("\\s", "").startsWith("/**")
                && lines.getLast().replaceAll("\\s", "").endsWith("*/");
    }

    public String getMessage() {
        return lines.toString();
    }

    public ArrayList<String> getBlockTags() {
        return blockTags;
    }

    public boolean hasTag(String blockTag) {
        return blockTags.contains(blockTag);
    }

    private static ArrayList<String> rawToLines(String rawlines) {
        return new ArrayList<String>(List.of(rawlines.split("\n")));
    }
}
