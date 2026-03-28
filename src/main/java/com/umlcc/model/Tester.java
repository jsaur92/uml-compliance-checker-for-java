package com.umlcc.model;

import java.io.File;

public class Tester {
    private static ComplianceCheckerApplication app;

    public static void main(String[] args) {
        app = ComplianceCheckerApplication.getInstance();
        test3();
    }

    // load a .umlcc file and print its contents.
    public static void test1() {
        Directory dir = app.loadUmlDataByUmlcc("strategy.umlcc");
        System.out.println(dir);
    }

    // load a directory, export as a .umlcc file, and print its contents.
    public static void test2() {
        String root = "/media/jsaur/Storage/Class/SCHC-497/DPA-S2026/observer";
        Directory dir = app.loadUmlDataByRepo(root);
        System.out.println(dir);
    }

    // bulk load all of the repos in a directory as .umlcc files.
    public static void test3() {
        String root = "/media/jsaur/Storage/Class/SCHC-497/DPA-S2026/";
        File r = new File(root);
        for (File f : r.listFiles()) {
            Directory d = app.loadUmlDataByRepo(f.getPath());
            app.saveAsUmlccFile(d);
        }
    }


}
