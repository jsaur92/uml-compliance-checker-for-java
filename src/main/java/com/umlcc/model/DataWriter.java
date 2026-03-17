package com.umlcc.model;

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
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean saveConfigData(HashMap<Warning, String> warningMessages) {
        return false;
    }
}
