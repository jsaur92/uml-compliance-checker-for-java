package com.umlcc.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
                    String content = "";
                    Scanner fileScanner = new Scanner(f);
                    while (fileScanner.hasNext()) {
                        content += fileScanner.next();
                    }
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
        File umlccFile = new File(UMLCC_DIR_PATH + pathname);
        String dirText = "";
        try {
            Scanner scanner = new Scanner(umlccFile);
            while (scanner.hasNext()) {
                dirText += scanner.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadDirFromUmlcc(dirText);
    }

    private static Directory loadDirFromUmlcc(String dirText) {
        String[] lines = dirText.split(DELIMITER_NEWLINE);
        String dirName = removeWhitespace(lines[0].split(DELIMITER_FILE_START)[0]);
        ArrayList<UserFile> files = new ArrayList<UserFile>();
        ArrayList<Directory> subdirs = new ArrayList<Directory>();
        String thisFileText = "";
        int baseTabs = countTabs(lines[0]);
        boolean isSubdir = false;
        for (int i = 1; i < lines.length; i++) {
            thisFileText += lines[i];
            if (lines[i].contains(DELIMITER_FILE_START)) {
                isSubdir = !lines[i].contains(DELIMITER_DOT);
            }
            else if (lines[i].contains(DELIMITER_FILE_END)) {
                if (isSubdir) {
                    Directory newDir = loadDirFromUmlcc(thisFileText);
                    subdirs.add(newDir);
                }
                else {
                    UserFile newFile = loadFileFromUmlcc(thisFileText);
                    files.add(newFile);
                }
                thisFileText = "";
            }
        }
        return new Directory(dirName, files, subdirs);
    }

    private static UserFile loadFileFromUmlcc(String fileText) {
        String[] lines = fileText.split(DELIMITER_NEWLINE);
        String fileName = removeWhitespace(lines[0].split(DELIMITER_FILE_START)[0]);
        HashMap<String, JavaClass> classes = new HashMap<String, JavaClass>();
        String thisClassText = "";
        int baseTabs = countTabs(lines[0]);
        for (int i = 1; i < lines.length; i++) {
            if (countTabs(lines[i]) > baseTabs) {
                thisClassText += lines[i];
            }
            else {
                JavaClass newClass = loadClassFromUmlcc(thisClassText);
                classes.put(newClass.getName(), newClass);
                thisClassText = "";
            }
        }
        if (classes.isEmpty())  return new UserFile(fileName, "");
        return new UserFile(fileName, classes, ModificationRule.DONT_CARE, "");
    }

    private static JavaClass loadClassFromUmlcc(String classText) {
        String[] lines = classText.split(DELIMITER_NEWLINE);
        String classLine = lines[0].replaceAll(DELIMITER_TAB, "");
        String[] parts = classLine.split(DELIMITER_SPACE);
        String name = parts[parts.length-1];
        ArrayList<Modifier> mods = parseMods(parts);
        boolean is_interface = parts[parts.length-2].equals(INTERFACE_DESIGNATION);

        ArrayList<JavaVariable> vars = new ArrayList<JavaVariable>();
        ArrayList<JavaMethod> methods = new ArrayList<JavaMethod>();
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].endsWith(DELIMITER_METHOD_END))
                methods.add(loadMethodFromUmlcc(lines[i]));
            else
                vars.add(loadVarFromUmlcc(lines[i]));
        }
        return new JavaClass(name, mods, "", vars, methods, is_interface);
    }

    private static JavaMethod loadMethodFromUmlcc(String line) {
        int paramStart = line.indexOf(DELIMITER_METHOD_START);
        String partsFull = line.substring(0, paramStart);
        String[] parts = partsFull.split(DELIMITER_SPACE);
        String name = parts[parts.length-1];
        String type = parts[parts.length-2];
        ArrayList<Modifier> mods = parseMods(parts);

        String paramsFull = line.substring(paramStart, line.length()-1);
        String[] params = paramsFull.split(", ");
        ArrayList<JavaVariable> p = new ArrayList<JavaVariable>();
        for (String s : params) p.add(loadVarFromUmlcc(s));

        return new JavaMethod(name, mods, "", p, type, "");
    }

    private static JavaVariable loadVarFromUmlcc(String line) {
        String[] parts = line.split(DELIMITER_SPACE);
        String name = parts[parts.length-1];
        String type = parts[parts.length-2];
        ArrayList<Modifier> mods = parseMods(parts);
        return new JavaVariable(name, mods, "", type);
    }

    private static ArrayList<Modifier> parseMods(String[] parts) {
        ArrayList<Modifier> mods = new ArrayList<Modifier>();
        for (int i = 0; i < parts.length-2; i++) {
            mods.add(Modifier.valueOf(parts[i].toUpperCase()));
        }
        return mods;
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
