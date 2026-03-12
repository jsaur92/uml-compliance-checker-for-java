package com.umlcc.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Directory {
    private HashMap<String, UserFile> files;
    private HashMap<String, Directory> subdirs;

    public Directory(ArrayList<UserFile> files, ArrayList<Directory> subdirs) {

    }

    public UserFile getFile(String name) {
        return null;
    }

    public Directory getSubdir(String name) {
        return null;
    }
}
