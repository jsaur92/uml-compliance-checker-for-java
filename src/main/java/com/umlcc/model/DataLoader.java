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

    /**
     * Load a Directory containing all the data from a .umlcc file.
     * @param pathname path to the .umlcc file.
     * @return the Directory.
     */
    public static Directory loadUmlcc(String pathname) {
        return null;
    }

    private Directory loadDirFromUmlcc(String dirText) {
        return null;
    }

    private UserFile loadFileFromUmlcc(String fileText) {
        String[] lines = fileText.split("\n");

        return null;
    }

    private JavaClass loadClassFromUmlcc(String[] lines) {
        String classLine = lines[0].replaceAll("\t", "");
        String[] parts = classLine.split(" ");
        String name = parts[parts.length-1];
        ArrayList<Modifier> mods = new ArrayList<Modifier>();
        for (int i = 0; i < parts.length-2; i++) {
            mods.add(Modifier.valueOf(parts[i].toUpperCase()));
        }
        boolean is_interface = parts[parts.length-2].equals("interface");

        ArrayList<JavaVariable> vars = new ArrayList<JavaVariable>();
        ArrayList<JavaMethod> methods = new ArrayList<JavaMethod>();
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].charAt(lines[i].length()-1) == ')')
                methods.add(loadMethodFromUmlcc(lines[i]));
            else
                vars.add(loadVarFromUmlcc(lines[i]));
        }
        return new JavaClass(name, mods, "", vars, methods, is_interface);
    }

    private JavaMethod loadMethodFromUmlcc(String line) {
        int paramStart = line.indexOf('(');
        String partsFull = line.substring(0, paramStart);
        String[] parts = partsFull.split(" ");
        String name = parts[parts.length-1];
        String type = parts[parts.length-2];
        ArrayList<Modifier> mods = new ArrayList<Modifier>();
        for (int i = 0; i < parts.length-2; i++) {
            mods.add(Modifier.valueOf(parts[i].toUpperCase()));
        }

        String paramsFull = line.substring(paramStart, line.length()-1);
        String[] params = paramsFull.split(", ");
        ArrayList<JavaVariable> p = new ArrayList<JavaVariable>();
        for (String s : params) p.add(loadVarFromUmlcc(s));

        return new JavaMethod(name, mods, "", p, type, "");
    }

    private JavaVariable loadVarFromUmlcc(String line) {
        String[] parts = line.split(" ");
        String name = parts[parts.length-1];
        String type = parts[parts.length-2];
        ArrayList<Modifier> mods = new ArrayList<Modifier>();
        for (int i = 0; i < parts.length-2; i++) {
            mods.add(Modifier.valueOf(parts[i].toUpperCase()));
        }
        return new JavaVariable(name, mods, "", type);
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
