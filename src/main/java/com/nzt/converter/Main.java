package com.nzt.converter;

import com.nzt.converter.fbx.FbxConverter;
import com.nzt.converter.fbx.FbxConverterOptions;
import com.nzt.converter.utils.OSType;
import com.nzt.converter.utils.PropertiesFileReader;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.Properties;

public class Main {

    public static OSType detectedOS = OSType.getOperatingSystemType();
    public static String BLENDER_TO_GDX_FOLDER_CONF = "/BlenderToGdxConfig";

    public static void main(String[] args) {
        System.out.println("===== Starting BlenderToGdx =====");
        System.out.println("[Init] OS =" + detectedOS.toString());

        URL locationJar = Main.class.getProtectionDomain().getCodeSource().getLocation();
        File file = new File(locationJar.getPath());
        Path jarPath = file.getParentFile().toPath();
        System.out.println("[Init] JarLocation = " + locationJar.toString());

        blenderToFbx();
        fbxToGdx(jarPath);
    }

    public static void blenderToFbx() {
        System.out.println("============================");
        System.out.println("Step 1 : Blender to Fbx");
        System.out.println("nothing ! not impl for now !");
        System.out.println("============================");
    }

    public static void fbxToGdx(Path jarPath) {
        System.out.println("============================");
        System.out.println("Step 2 : Fbx To GDX");
        System.out.println("============================");
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
        Properties properties = propertiesFileReader.read(jarPath.toString() + BLENDER_TO_GDX_FOLDER_CONF + "/fbx-to-gdx.properties");
        if (properties == null) {
            System.err.println("fbx-to-gdx.properties is required");
        }
        FbxConverterOptions options = new FbxConverterOptions(jarPath, properties);


        FbxConverter fbxConverter = new FbxConverter(options);
        fbxConverter.readDbAndConvertAll();
    }
}
