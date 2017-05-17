package com.fragmgia.photoeditor.util;

import java.io.File;

/**
 * Created by trungnguyens93gmail.com on 5/9/17.
 */
public class FileManager {
    public static void deleteDirectory(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i=0; i<files.length; i++) {
                    if (files[i].isDirectory()) deleteDirectory(files[i]);
                    else files[i].delete();
                }
            }
            file.delete();
        }
    }
}
