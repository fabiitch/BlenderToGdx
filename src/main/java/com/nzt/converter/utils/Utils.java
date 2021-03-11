package com.nzt.converter.utils;

import java.io.File;

public class Utils {
    private static String OS = System.getProperty("os.name");

    public static String replacePath(String path) {
        path = path.replace("\\", File.separator);
        if (OS.startsWith("Windows")) {
            path = path.substring(1);
        }
        return path;
    }
}
