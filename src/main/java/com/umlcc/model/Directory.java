package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data for a directory of a project.
 * @author Joe Hardy
 */
public class Directory {
    private String name;
    private HashMap<String, UserFile> files;
    private HashMap<String, Directory> subdirs;

    /**
     * Constructor for Directory class.
     * @param name the name of this directory.
     * @param files all of the files in this directory.
     * @param subdirs all of this directory's subdirectories.
     */
    public Directory(String name, ArrayList<UserFile> files, ArrayList<Directory> subdirs) {
        this.name = name;
        this.files = new HashMap<String, UserFile>();
        this.subdirs = new HashMap<String, Directory>();
        for (UserFile file : files) {
            this.files.put(file.getName(), file);
        }
        for (Directory dir : subdirs) {
            this.subdirs.put(dir.getName(), dir);
        }
    }

    /**
     * Accessor method for this directory's name.
     * @return the name of this directory.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks to see if a given file exists in this directory.
     * @param name the name of the file to check for.
     * @return true if files has that file, false otherwise.
     */
    public boolean hasFile(String name) {
        return files.containsKey(name);
    }

    /**
     * Checks to see if a given subdirectory exists in this directory.
     * @param name the name of the subdirectory to check for.
     * @return true if subdirs has that directory, false otherwise.
     */
    public boolean hasSubdir(String name) {
        return subdirs.containsKey(name);
    }

    /**
     * Accesses a given file from files.
     * @param name the file to get.
     * @return the file with the given name.
     */
    public UserFile getFile(String name) {
        return files.get(name);
    }

    /**
     * Access a given subdirectory from subdirs.
     * @param name the subdir to get.
     * @return the subdirectory with the given name.
     */
    public Directory getSubdir(String name) {
        return subdirs.get(name);
    }

    /**
     * Accessor method for files.
     * @return the files HashMap.
     */
    public HashMap<String, UserFile> getFiles() {
        return files;
    }

    /**
     * Mutator method for files.
     * @param files the given files HashMap.
     */
    public void setFiles(HashMap<String, UserFile> files) {
        this.files = files;
    }

    /**
     * Accessor method for subdirs.
     * @return the subdirs HashMap.
     */
    public HashMap<String, Directory> getSubdirs() {
        return subdirs;
    }

    /**
     * Mutator method for subdirs.
     * @param subdirs the given subdirs HashMap.
     */
    public void setSubdirs(HashMap<String, Directory> subdirs) {
        this.subdirs = subdirs;
    }

    @Override
    public String toString() {
        String header = getName() + " {";
        String body = "";
        for (String key : subdirs.keySet()) {
            body += "\n" + subdirs.get(key);
        }
        for (String key : files.keySet()) {
            body += "\n" + files.get(key);
        }
        body = body.replaceAll("\n", "\n\t");   //indent the body
        return header + body;
    }
}
