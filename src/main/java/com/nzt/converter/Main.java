package com.nzt.converter;

import com.nzt.converter.fbx.FbxConverter;

public class Main {


    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            System.out.println("Need Fbx folder path and G3DB/G3DJ folder path");
            System.exit(0);
        }
        String fbxFolderPath = args[0];
        String gdxExportPath = args[1];
        if (args.length == 3 && args[2] == "isRelativePath") {
            String initPath = System.getProperty("user.dir");
            System.out.println("Working Directory = " + initPath);
            fbxFolderPath = initPath + "/" + fbxFolderPath;
            gdxExportPath = initPath + "/" + gdxExportPath;
        }
        Main main = new Main(fbxFolderPath, gdxExportPath);
    }

    public Main(String fbxFolderPath, String gdxExportPath) {
        FbxConverter fbxConverter = new FbxConverter(fbxFolderPath, gdxExportPath);
        fbxConverter.readDbAndConvertAll();
    }
}
