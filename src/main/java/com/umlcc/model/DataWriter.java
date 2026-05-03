package com.umlcc.model;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Class for statically writing out JSON data and .umlcc files.
 * @author Joe Hardy
 */
public class DataWriter extends DataConstants {
    /**
     * Save a directory as a .umlcc file to the umlcc directory.
     * @param dir the directory to save.
     * @return true if saving successful, false otherwise.
     */
    public static boolean saveAsUmlccFile(Directory dir) {
        try {
            String dirName = dir.getName();
            if (dirName.endsWith(DELIMITER_SLASH)) dirName = dirName.substring(0, dirName.length()-1);
            FileWriter writer = new FileWriter(UMLCC_DIR_PATH + dirName + UMLCC_FILE_EXTENSION);
            writer.write(dir.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Write the User data to the user.json file.
     * @param user the User to save.
     * @return true if the method ends successfully.
     */
    public static boolean saveUser(User user) {
        JSONObject userJson = new JSONObject();
        userJson.put(USER_TYPE, user.getUserType());
        userJson.put(USER_LAST_UML, user.getLastUmlDataPath());
        userJson.put(USER_LAST_MY_CODE, user.getLastMyCodePath());
        userJson.put(USER_DEFAULT_CLONE, user.getDefaultCloneParent());
        userJson.put(USER_DELETE_CLONED, user.getDeleteClonedOnClose());
        userJson.put(USER_CLONE_PATTERN, user.getCloneIntoPattern());
        try {
            FileWriter writer = new FileWriter(JSON_DIR_PATH + USER_FILE_NAME);
            writer.write(userJson.toString(JSON_INDENT));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Write the Config data to the config.json file.
     * @param warningMessages the warning messages of the Config to save.
     * @return true if the method ends successfully.
     */
    public static boolean saveConfigData(HashMap<Warning, String> warningMessages) {
        JSONObject configJson = new JSONObject();
        JSONArray wmsArray= new JSONArray();
        for (Warning key : warningMessages.keySet()) {
            JSONObject wm = new JSONObject();
            wm.put(CONFIG_WARNINGS_WARNING, key.toString());
            wm.put(CONFIG_WARNINGS_MESSAGE, warningMessages.get(key));
            wmsArray.put(wm);
        }
        configJson.put(CONFIG_WARNINGS, wmsArray);
        try {
            FileWriter writer = new FileWriter(JSON_DIR_PATH + CONFIG_FILE_NAME);
            writer.write(configJson.toString(JSON_INDENT));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Attempt to pull or clone a Git repo.
     * @param url the url to the remote Git repo.
     * @param parentDirPath the path to the directory to clone/pull into.
     * @param outputText a StringBuilder representing the output of the commands.
     * @return true if successfully cloned or pulled, false otherwise.
     */
    public static boolean attemptPullGitRepo(String url, String parentDirPath, StringBuilder outputText) {
        String[] commandClone = {
                "git",
                "clone",
                url,
                parentDirPath
        };
        String[] commandPull = {
                "git",
                "-C",
                parentDirPath,
                "pull"
        };

        try {
            Process procClone = Runtime.getRuntime().exec(commandClone);
            String cloneOut = getCommandOutput(procClone);

            outputText.append("Attempting to clone.\n");
            outputText.append(cloneOut);
            if (!cloneOut.startsWith("fatal:")) {
                outputText.append("Clone successful.\n");
            } else {
                outputText.append("Clone failed. Attempting to pull.\n");
                Process procPull = Runtime.getRuntime().exec(commandPull);
                String pullOut = getCommandOutput(procPull);
                outputText.append(pullOut);
                if (!pullOut.startsWith("fatal:")) {
                    outputText.append("Pull successful.\n");
                } else {
                    outputText.append("Pull failed.\n");
                    return false;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // modified from https://stackoverflow.com/questions/5711084/java-runtime-getruntime-getting-output-from-executing-a-command-line-program
    private static String getCommandOutput(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String s = "";

        // Read the output from the command
        String output = null;
        while ((output = stdInput.readLine()) != null) {
            s += output + "\n";
        }

        String error = null;
        // Read any errors from the attempted command
        while ((error = stdError.readLine()) != null) {
            s += error + "\n";
        }

        return s;
    }
}
