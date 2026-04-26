package com.umlcc.controller;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Contains helper methods used across several Controllers.
 */
public class ControllerUtil {
    // works for FileChooser and DirectoryChooser objects.
    public static void setInitialDirectory(Object chooser, String path) {
        if (path != null) {
            File f = new File(path);
            if (f.exists()) {
                if (!f.isDirectory()) f = f.getParentFile();
                if (chooser instanceof FileChooser) ((FileChooser) chooser).setInitialDirectory(f);
                if (chooser instanceof DirectoryChooser) ((DirectoryChooser) chooser).setInitialDirectory(f);
            }
        }
    }
}
