package com.nzt.converter;

import com.nzt.converter.fbx.FbxConverter;
import com.nzt.converter.fbx.FbxConverterOptions;
import com.nzt.converter.utils.PropertiesFileReader;

import java.util.Locale;
import java.util.Properties;

public class Main {

    public static OSType detectedOS;

    public static void main(String[] args) {
        getOperatingSystemType();
        if (detectedOS == OSType.Other) {
            System.err.println("Bad OS detected, need window, macos or linux");
            System.exit(0);
        }

        PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
        String initPath = System.getProperty("user.dir");

        Properties properties = propertiesFileReader.read(initPath + "/fbx-to-gdx.properties");
        if (properties == null) {
            System.err.println("fbx-to-gdx.properties is required");
        }
        FbxConverterOptions options = new FbxConverterOptions(properties);

        FbxConverter fbxConverter = new FbxConverter(options);
        fbxConverter.readDbAndConvertAll();
    }

    public enum OSType {
        Windows, MacOS, Linux, Other
    }

    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
            if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
                detectedOS = OSType.MacOS;
            } else if (OS.indexOf("win") >= 0) {
                detectedOS = OSType.Windows;
            } else if (OS.indexOf("nux") >= 0) {
                detectedOS = OSType.Linux;
            } else {
                detectedOS = OSType.Other;
            }
        }
        return detectedOS;
    }
}
