package com.umlcc.model;

import java.util.HashMap;

public class Config {
    private static Config config;
    private HashMap<Warning, String> warningMessages;

    /**
     * Constructor for Config class.
     * @param warningMessages the HashMap of Warnings to the warning messages.
     */
    private Config(HashMap<Warning, String> warningMessages) {
        this.warningMessages = warningMessages;
    }

    /**
     * Get the static Config instance of this session, or make one if there
     * isn't one already.
     * @return the Config instance.
     */
    public static Config getInstance() {
        if (config == null) {
            config = new Config(DataLoader.loadConfigData());
        }
        return config;
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
