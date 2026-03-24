package com.umlcc.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.HashMap;

public class DataWriter extends DataConstants {
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
        try {
            FileWriter writer = new FileWriter(USER_FILE_NAME);
            writer.write(userJson.toString());
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
            wm.put(CONFIG_WARNINGS_WARNING, key);
            wm.put(CONFIG_WARNINGS_MESSAGE, warningMessages.get(key));
            wmsArray.add(wm);
        }
        configJson.put(CONFIG_WARNINGS, wmsArray);
        try {
            FileWriter writer = new FileWriter(USER_FILE_NAME);
            writer.write(configJson.toString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
