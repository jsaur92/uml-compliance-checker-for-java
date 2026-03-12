package com.umlcc.model;

import java.util.HashMap;

public class Config {
    private static Config config;
    private static HashMap<Warning, String> warningMessages;

    private Config() {

    }

    private Config(HashMap<Warning, String> warningMessages) {

    }

    public static Config getInstance() {
        return null;
    }

    public boolean hasWarning(Warning warning) {
        return false;
    }

    public String getWarningMessage(Warning warning) {
        return null;
    }

    public boolean setWarningMessage(Warning warning, String message) {
        return false;
    }

    public boolean removeWarningMessage(Warning warning) {
        return false;
    }
}
