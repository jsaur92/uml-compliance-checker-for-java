package com.umlcc.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Static class for loading in JSON data and Java project repositories.
 * @author Joe Hardy
 */
public class DataLoader extends DataConstants {
    /**
     * Load a Directory containing all the data from a real Java project repository.
     * @param rootname the root of the file
     * @return the Directory.
     */
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
                dirText += scanner.nextLine() + DELIMITER_NEWLINE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loadDirFromUmlcc(dirText);
    }

    private static Directory loadDirFromUmlcc(String dirText) {
        String[] lines = dirText.split(DELIMITER_NEWLINE);
        String dirName = removeWhitespace(lines[0].split(REGEX_FILE_START)[0]);
        ArrayList<UserFile> files = new ArrayList<UserFile>();
        ArrayList<Directory> subdirs = new ArrayList<Directory>();
        String thisFileText = "";
        int baseTabs = countTabs(lines[0]);
        boolean isSubdir = false;
        boolean open = false;   // true when a file is opened, false when it is closed. prevents catching sequential "{"s as their own files.
        for (int i = 1; i < lines.length; i++) {
            thisFileText += lines[i] + "\n";
            if (lines[i].contains(DELIMITER_FILE_START)) {
                open = true;
                thisFileText = lines[i] + "\n";
                isSubdir = !lines[i].contains(DELIMITER_DOT);
            }
            else if (lines[i].contains(DELIMITER_FILE_END) && open) {
                open = false;
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
        String fileName = removeWhitespace(lines[0].split(REGEX_FILE_START)[0]);
        HashMap<String, JavaClass> classes = new HashMap<String, JavaClass>();
        String thisClassText = "";
        int baseTabs = countTabs(lines[0]);
        for (int i = 1; i < lines.length; i++) {
            if (countTabs(lines[i]) > baseTabs || thisClassText.equals("")) {
                thisClassText += lines[i] + "\n";
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
        boolean is_interface = false;
        String name = "";
        for (int i = 0; i < parts.length; i++) {     // iterate to find where it says 'class' or 'interface'.
            if (parts[i].equals(CLASS_DESIGNATION)) {
                is_interface = false;
                name = parts[i+1];
                break;
            }
            else if (parts[i].equals(INTERFACE_DESIGNATION)) {
                is_interface = true;
                name = parts[i+1];
                break;
            }
        }
        ArrayList<Modifier> mods = parseMods(parts);

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
        if (Modifier.hasValue(removeWhitespace(type))) type = null;
        ArrayList<Modifier> mods = parseMods(parts);

        String paramsFull = line.substring(paramStart+1, line.length()-1);
        String[] params = paramsFull.split(", ");
        ArrayList<JavaVariable> p = new ArrayList<JavaVariable>();
        for (String s : params) {
            if (s.split(" ").length > 1) {
                p.add(loadVarFromUmlcc(s));
            }
        }

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
        for (String part : parts) {
            part = removeWhitespace(part);
            if (Modifier.hasValue(part)) {
                mods.add(Modifier.fromString(part));
            }
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
            JSONObject userJson = (JSONObject) parser.parse(new FileReader(JSON_DIR_PATH + USER_FILE_NAME));
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

    /**
     * Load the config data from the config.json file.
     * @return the HashMap with warningMessage data for Config.
     */
    public static HashMap<Warning, String> loadConfigData() {
        JSONParser parser = new JSONParser();
        HashMap<Warning, String> warningMessages = new HashMap<Warning, String>();
        try {
            JSONObject configJson = (JSONObject) parser.parse(new FileReader(JSON_DIR_PATH + CONFIG_FILE_NAME));
            JSONArray wmArray = (JSONArray) configJson.get(CONFIG_WARNINGS);
            for (Object obj : wmArray) {
                JSONObject wm = (JSONObject) obj;
                warningMessages.put( (Warning) wm.get(CONFIG_WARNINGS_WARNING),
                                     (String) wm.get(CONFIG_WARNINGS_MESSAGE) );
            }
        } catch (Exception ignored) {}
        return warningMessages;
    }
}
