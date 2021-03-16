package com.nzt.converter;

import com.nzt.converter.fbx.FbxConverter;
import com.nzt.converter.fbx.FbxConverterOptions;
import com.nzt.converter.utils.OSType;
import com.nzt.converter.utils.PropertiesFileReader;

import java.util.Locale;
import java.util.Properties;

public class Main {

    public static OSType detectedOS = OSType.getOperatingSystemType();

    public static String BLENDER_TO_GDX_FOLDER_CONF = "/BlenderToGdx";

    public static void main(String[] args) {
        if (detectedOS == OSType.Other) {
            System.err.println("Bad OS detected, need window, macos or linux");
            System.exit(0);
        }

        PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
        String initPath = System.getProperty("user.dir");

        Properties properties = propertiesFileReader.read(initPath + BLENDER_TO_GDX_FOLDER_CONF + "/fbx-to-gdx.properties");
        if (properties == null) {
            System.err.println("fbx-to-gdx.properties is required");
        }
        FbxConverterOptions options = new FbxConverterOptions(properties);

        FbxConverter fbxConverter = new FbxConverter(options);
        fbxConverter.readDbAndConvertAll();
    }
}
