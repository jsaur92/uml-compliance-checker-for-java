package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data for a directory of a project.
 * @author Joe Hardy
 */
public class Directory {
    private String name;
    private ArrayList<Directory> subdirs;
    private ArrayList<UserFile> files;

    /**
     * Constructor for Directory class.
     * @param name the name of this directory.
     * @param subdirs all of this directory's subdirectories.
     * @param files all of the files in this directory.
     */
    public Directory(String name, ArrayList<Directory> subdirs, ArrayList<UserFile> files) {
        this.name = name;
        this.subdirs = subdirs;
        this.files = files;
    }

    /**
     * Accessor method for this directory's name.
     * @return the name of this directory.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks to see if a given subdirectory exists in this directory.
     * @param name the name of the subdirectory to check for.
     * @return true if subdirs has that directory, false otherwise.
     */
    public boolean hasSubdir(String name) {
        for (Directory dir : subdirs) {
            if (dir.getName().equals(name)) return true;
        }
        return false;
    }

    /**
     * Checks to see if a given file exists in this directory.
     * @param name the name of the file to check for.
     * @return true if files has that file, false otherwise.
     */
    public boolean hasFile(String name) {
        for (UserFile file : files) {
            if (file.getName().equals(name)) return true;
        }
        return false;
    }

    /**
     * Access a given subdirectory from subdirs.
     * @param name the subdir to get.
     * @return the subdirectory with the given name.
     */
    public Directory getSubdir(String name) {
        for (Directory dir : subdirs) {
            if (dir.getName().equals(name)) return dir;
        }
        return null;
    }

    /**
     * Accesses a given file from files.
     * @param name the file to get.
     * @return the file with the given name.
     */
    public UserFile getFile(String name) {
        for (UserFile file : files) {
            if (file.getName().equals(name)) return file;
        }
        return null;
    }

    /**
     * Accessor method for files.
     * @return the files HashMap.
     */
    public ArrayList<UserFile> getFiles() {
        return files;
    }

    /**
     * Mutator method for files.
     * @param files the given files HashMap.
     */
    public void setFiles(ArrayList<UserFile> files) {
        this.files = files;
    }

    /**
     * Accessor method for subdirs.
     * @return the subdirs HashMap.
     */
    public ArrayList<Directory> getSubdirs() {
        return subdirs;
    }

    /**
     * Mutator method for subdirs.
     * @param subdirs the given subdirs HashMap.
     */
    public void setSubdirs(ArrayList<Directory> subdirs) {
        this.subdirs = subdirs;
    }

    /**
     * Compiles all of the compliance checks of this repository's files.
     * @return the list of EvaluationResults resulting from this compliance check.
     */
    public ArrayList<EvaluationResult> checkCompliance() {
        ArrayList<EvaluationResult> results = new ArrayList<EvaluationResult>();
        for (UserFile file : files) {
            results.addAll(file.checkCompliance());
        }
        for (Directory dir : subdirs) {
            results.addAll(dir.checkCompliance());
        }
        return results;
    }

    /**
     * Compiles all of the compliance checks of this repository's files.
     * @param other the other directory to compare this one to.
     * @return the list of EvaluationResults resulting from this compliance check.
     */
    public ArrayList<EvaluationResult> checkCompliance(Directory other) {
        ArrayList<EvaluationResult> results = new ArrayList<EvaluationResult>();
        for (UserFile file : files) {
            results.addAll(file.checkCompliance(other.getFile(file.getName())));
        }
        for (Directory dir : subdirs) {
            results.addAll(dir.checkCompliance(other));
        }
        return results;
    }

    @Override
    public String toString() {
        String header = getName() + " {";
        String body = "";
        for (Directory dir : subdirs) {
            body += "\n" + dir;
        }
        for (UserFile file : files) {
            body += "\n" + file;
        }
        body = body.replaceAll("\n", "\n\t");   //indent the body
        return header + body + "\n}";
    }
}
