package com.nzt.converter.utils;

import java.io.File;

public class Utils {
    private static String OS = System.getProperty("os.name");

    public static String replacePath(String path) {
        path = path.replace("\\", File.separator);
        if (OS.startsWith("Windows") && path.startsWith("/")) {
            path = path.substring(1);
        }
        return path;
    }

    public static String replacePathForFbx(String path) {
        path = replacePath(path);
        path = path.replace("\\", "/");
        return path;
    }

    public static boolean createAllFolderForPath(String startPath, String pathFile) {
        String[] split = pathFile.split("/");
        String s = startPath;
        boolean ok = true;
        for (int i = 0; i < split.length; i++) {
            s += "/" + split[i];
            File file = new File(s);
            if (!file.exists()) {
                ok &= file.mkdir();
            }
        }
        return ok;
    }
}
