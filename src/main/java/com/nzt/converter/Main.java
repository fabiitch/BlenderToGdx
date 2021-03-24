package com.nzt.converter;

import com.nzt.converter.fbx.FbxConverter;
import com.nzt.converter.fbx.FbxConverterOptions;
import com.nzt.converter.utils.OSType;
import com.nzt.converter.utils.PropertiesFileReader;

import java.util.Properties;

public class Main {

    public static OSType detectedOS = OSType.getOperatingSystemType();
    public static String BLENDER_TO_GDX_FOLDER_CONF = "/BlenderToGdxConfig";

    public static void main(String[] args) {
        System.out.println("===== Starting BlenderToGdx =====");
        if (detectedOS == OSType.Other) {
            System.err.println("Bad OS detected, need window, macos or linux");
            System.exit(0);
        }
        System.out.println("[Init] OS =" + detectedOS.toString());
        String userDir = System.getProperty("user.dir");
        System.out.println("[Init] user.dir =" + userDir);
        String jarRelativePath = "";
        if (args != null && args.length == 1) {
            jarRelativePath = args[0];
            System.out.println("[Init] jarRelativePath =" + jarRelativePath);
        }
        blenderToFbx();
        fbxToGdx(userDir + jarRelativePath);
    }

    public static void blenderToFbx() {
        System.out.println("============================");
        System.out.println("Step 2 : Blender to Fbx");
        System.out.println("nothing !");
        System.out.println("============================");
    }

    public static void fbxToGdx(String startPath) {
        System.out.println("============================");
        System.out.println("Step 1 : Fbx To GDX");
        System.out.println("============================");
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
        Properties properties = propertiesFileReader.read(startPath + BLENDER_TO_GDX_FOLDER_CONF + "/fbx-to-gdx.properties");
        if (properties == null) {
            System.err.println("fbx-to-gdx.properties is required");
        }
        FbxConverterOptions options = new FbxConverterOptions(startPath, properties);


        FbxConverter fbxConverter = new FbxConverter(options);
        fbxConverter.readDbAndConvertAll();
    }
}
