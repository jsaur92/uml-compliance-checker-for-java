package com.umlcc.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class for statically loading in JSON data and Java project repositories.
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
            ArrayList<UserFile> files = new ArrayList<UserFile>();
            ArrayList<Directory> dirs = new ArrayList<Directory>();
            for (File f : rootDir.listFiles()) {
                if (f.isDirectory()) {
                    dirs.add( loadRepo(f.getPath()));
                }
                else {
                    files.add(loadFileFromRepo(f));
                }
            }
            dir = new Directory(rootDir.getName()+"/", dirs, files);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dir;
    }

    private static UserFile loadFileFromRepo(File file) {
        String content = "";
        ArrayList<JavaClass> classes = new ArrayList<JavaClass>();
        try {
            Scanner fileScanner = new Scanner(file);
            String classText = "";
            int open_layer = 0;
            while (fileScanner.hasNext()) {
                String next = fileScanner.nextLine();
                content += next + DELIMITER_NEWLINE;
                if (next.contains(DELIMITER_FILE_START)) {
                    open_layer++;
                }
                if (open_layer >= 1) {
                    classText += next + DELIMITER_NEWLINE;
                }
                if (next.contains(DELIMITER_FILE_END)) {
                    open_layer--;
                    if (open_layer == 0) {
                        classes.add(loadClassFromRepoText(classText));
                        classText = "";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new UserFile(file.getName(), classes, ModificationRule.DONT_CARE, content);
    }

    private static JavaClass loadClassFromRepoText(String text) {
        String[] lines = text.split(DELIMITER_NEWLINE);

        Object[] headerData = parseClassHeader(lines[0]);
        String name = (String) headerData[0];
        ArrayList<Modifier> mods = (ArrayList<Modifier>) headerData[1];
        ArrayList<String> inherited = (ArrayList<String>) headerData[2];
        String implemented = (String) headerData[3];
        String classType = (String) headerData[4];

        ArrayList<JavaVariable> vars = new ArrayList<JavaVariable>();
        ArrayList<JavaMethod> methods = new ArrayList<JavaMethod>();
        int open_layer = 0;
        String thisText = "";
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].contains(DELIMITER_SEMICOLON) && open_layer == 0) {
                // find abstract methods
                if (lines[i].contains(DELIMITER_METHOD_START)) {
                    methods.add(loadMethodFromRepoText(lines[i]));
                }
                // find attribute variables
                else {
                    vars.add(loadVarFromRepoText(lines[i]));
                }
            }
            // find non-abstract methods
            if (lines[i].contains(DELIMITER_FILE_START)) {
                open_layer++;
            }
            if (open_layer >= 1) {
                thisText += lines[i] + DELIMITER_NEWLINE;
            }
            if (lines[i].contains(DELIMITER_FILE_END)) {
                open_layer--;
                if (open_layer == 0) {
                    methods.add(loadMethodFromRepoText(thisText));
                    thisText = "";
                }
            }
        }
        return new JavaClass(name, mods, "", vars, methods, inherited, implemented, classType);
    }

    private static JavaMethod loadMethodFromRepoText(String text) {
        String header = text.split(DELIMITER_NEWLINE)[0];
        if (header.endsWith(DELIMITER_FILE_START) || header.endsWith(DELIMITER_NEWLINE)) header = header.substring(0, header.length()-1);
        JavaMethod method = loadMethodFromLine(header);
        method.setContent(text);
        return method;
    }

    private static JavaVariable loadVarFromRepoText(String text) {
        if (text.endsWith(DELIMITER_SEMICOLON)) text = text.substring(0, text.length()-1);
        return loadVarFromLine(text);
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
        return new Directory(dirName, subdirs, files);
    }

    private static UserFile loadFileFromUmlcc(String fileText) {
        String[] lines = fileText.split(DELIMITER_NEWLINE);
        String fileName = removeWhitespace(lines[0].split(REGEX_FILE_START)[0]);
        ArrayList<JavaClass> classes = new ArrayList<JavaClass>();
        String thisClassText = "";
        int baseTabs = countTabs(lines[0]);
        for (int i = 1; i < lines.length; i++) {
            if (countTabs(lines[i]) > baseTabs || thisClassText.equals("")) {
                thisClassText += lines[i] + "\n";
            }
            else {
                JavaClass newClass = loadClassFromUmlcc(thisClassText);
                classes.add(newClass);
                thisClassText = "";
            }
        }
        if (classes.isEmpty())  return new UserFile(fileName, "");
        return new UserFile(fileName, classes, ModificationRule.DONT_CARE, "");
    }

    private static JavaClass loadClassFromUmlcc(String classText) {
        String[] lines = classText.split(DELIMITER_NEWLINE);
        Object[] headerData = parseClassHeader(lines[0]);
        String name = (String) headerData[0];
        ArrayList<Modifier> mods = (ArrayList<Modifier>) headerData[1];
        ArrayList<String> inherited = (ArrayList<String>) headerData[2];
        String implemented = (String) headerData[3];
        String classType = (String) headerData[4];

        ArrayList<JavaVariable> vars = new ArrayList<JavaVariable>();
        ArrayList<JavaMethod> methods = new ArrayList<JavaMethod>();
        for (int i = 1; i < lines.length; i++) {
            if (lines[i].endsWith(DELIMITER_METHOD_END))
                methods.add(loadMethodFromLine(lines[i]));
            else
                vars.add(loadVarFromLine(lines[i]));
        }
        return new JavaClass(name, mods, "", vars, methods, inherited, implemented, classType);
    }

    private static Object[] parseClassHeader(String classLine) {
        classLine = classLine.replaceAll(DELIMITER_TAB, "");
        String[] parts = classLine.split(DELIMITER_SPACE);
        String name = "";
        ArrayList<String> inherited = new ArrayList<String>();
        String implemented = null;
        String classType = "class";
        for (int i = 0; i < parts.length; i++) {     // iterate to find where it says 'class' or 'interface'.
            if (parts[i].equals(CLASS_DESIGNATION)) {
                classType = "class";
                name = parts[i+1];
            }
            else if (parts[i].equals(INTERFACE_DESIGNATION)) {
                classType = "interface";
                name = parts[i+1];
            }
            else if (parts[i].equals(ENUM_DESIGNATION)) {
                classType = "enum";
                name = parts[i+1];
            }
            else if (parts[i].equals(EXTENDS_DESIGNATION)) {
                for (int j = i+1; (j < parts.length) && (!parts[j].equals(IMPLEMENTS_DESIGNATION)); j++) {
                    String word = removeWhitespace(parts[j]).replaceAll(DELIMITER_COMMA+"|"+REGEX_FILE_START, "");
                    if (word.length() > 0)
                        inherited.add(word);
                }
            }
            else if (parts[i].equals(IMPLEMENTS_DESIGNATION)) {
                implemented = parts[i+1];
            }
        }
        ArrayList<Modifier> mods = parseMods(parts);
        return new Object[] {name, mods, inherited, implemented, classType};
    }

    private static JavaMethod loadMethodFromLine(String line) {
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
                p.add(loadVarFromLine(s));
            }
        }

        return new JavaMethod(name, mods, "", p, type, "");
    }

    private static JavaVariable loadVarFromLine(String line) {
        String[] parts = line.split(DELIMITER_SPACE);
        String name = parts[parts.length-1];
        if (name.endsWith(DELIMITER_METHOD_END)) name = name.substring(0, name.length()-1);
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
        User user;
        try {
            String source = "";
            Scanner sourceScanner = new Scanner(new File(JSON_DIR_PATH + USER_FILE_NAME));
            while (sourceScanner.hasNext()) source += sourceScanner.nextLine() + DELIMITER_NEWLINE;
            JSONObject userJson = new JSONObject(source);
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
        HashMap<Warning, String> warningMessages = new HashMap<Warning, String>();
        try {
            String source = "";
            Scanner sourceScanner = new Scanner(new File(JSON_DIR_PATH + CONFIG_FILE_NAME));
            while (sourceScanner.hasNext()) source += sourceScanner.nextLine() + DELIMITER_NEWLINE;
            JSONObject configJson = new JSONObject(source);
            JSONArray wmArray = (JSONArray) configJson.get(CONFIG_WARNINGS);
            for (Object obj : wmArray) {
                JSONObject wm = (JSONObject) obj;
                warningMessages.put( Warning.valueOf((String) wm.get(CONFIG_WARNINGS_WARNING)),
                                     (String) wm.get(CONFIG_WARNINGS_MESSAGE) );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return warningMessages;
    }
}
