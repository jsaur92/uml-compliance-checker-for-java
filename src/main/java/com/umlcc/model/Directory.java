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
}
