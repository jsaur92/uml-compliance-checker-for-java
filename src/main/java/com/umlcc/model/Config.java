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

    /**
     * Checks if the current configuration settings has a warning message for
     * a given warning.
     * @param warning the Warning to check for
     * @return true if this warning is a key for warningMessages, else false.
     */
    public boolean hasWarning(Warning warning) {
        return warningMessages.containsKey(warning);
    }

    /**
     * Accessor method for a given warning message.
     * @param warning the warning to get a message for.
     * @return the message.
     */
    public String getWarningMessage(Warning warning) {
        return warningMessages.get(warning);
    }

    /**
     * Updates the message for a given warning if that warning is included in
     * warningMessages already, otherwise add that warning and its message.
     * @param warning the warning to set the message of.
     * @param message the message to set.
     * @return true if successful, false if the warningMessage with the given
     * warning is already set to the given message.
     */
    public boolean setWarningMessage(Warning warning, String message) {
        if (warningMessages.containsKey(warning) && warningMessages.get(warning).equals(message))
            return false;
        warningMessages.put(warning, message);
        return true;
    }

    /**
     * Removes a given warning from warningMessages.
     * @param warning the warning to remove.
     * @return true if successfully removed, false if the warning is already
     * not included.
     */
    public boolean removeWarningMessage(Warning warning) {
        if (!hasWarning(warning)) return false;
        warningMessages.remove(warning);
        return true;
    }
}
