package com.umlcc.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Static class for loading in JSON data and Java project repositories.
 * @author Joe Hardy
 */
public class DataLoader extends DataConstants {
    public static Directory loadRepo(String rootname) {
        Directory dir;
        try {
            File rootDir = new File(rootname);
            FileReader reader = new FileReader(rootDir);
            ArrayList<UserFile> files = new ArrayList<UserFile>();
            ArrayList<Directory> dirs = new ArrayList<Directory>();
            for (File f : rootDir.listFiles()) {
                if (f.isDirectory()) {
                    dirs.add( loadRepo(f.getPath()));
                }
                else {
                    FileReader contentReader = new FileReader(f);
                    String content = contentReader.readAllAsString();
                    files.add(new UserFile(f.getName(), content));
                }
            }
            dir = new Directory(rootDir.getName(), files, dirs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dir;
    }

    public static Directory loadUmlcc(String pathname) {
        return null;
    }

    /**
     * Load the user data from the user.json file.
     * @return the User object from the user.json file.
     */
    public static User loadUser() {
        JSONParser parser = new JSONParser();
        User user;
        try {
            JSONObject userJson = (JSONObject) parser.parse(new FileReader(USER_FILE_NAME));
            UserType type = (UserType) userJson.get(USER_TYPE);
            String lastUml = (String) userJson.get(USER_LAST_UML);
            String lastMyCode = (String) userJson.get(USER_LAST_MY_CODE);
            user = new User(type, lastUml, lastMyCode);
        } catch (Exception e) {
            user = new User(UserType.BASIC, "", "");
            e.printStackTrace();
        }
        return user;
    }

    public static HashMap<Warning, String> loadConfigData() {
        return null;
    }
}
