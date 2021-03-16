package com.nzt.converter.utils;

import java.util.Locale;

public enum OSType {
    Windows, MacOS, Linux, Other;

    public static OSType getOperatingSystemType() {
        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
            return MacOS;
        } else if (OS.indexOf("win") >= 0) {
            return Windows;
        } else if (OS.indexOf("nux") >= 0) {
            return Linux;
        } else {
            return Other;
        }
    }

}
