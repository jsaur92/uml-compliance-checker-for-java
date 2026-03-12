package com.umlcc.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataLoader extends DataConstants {
    public static Directory loadRepo(String rootname) {
        return null;
    }

    public static Directory loadUmlcc(String pathname) {
        return null;
    }

    /**
     * Load the user data from the user.json file.
     * @return the User object from the user.json file.
     */
    public static User loadUser() throws FileNotFoundException {
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
