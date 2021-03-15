package com.nzt.converter.fbx;

import com.nzt.converter.Main;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FbxCopyResources {

    public void copyResourcesFiles(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        if (Main.detectedOS == Main.OSType.Windows) {
            File fbxConvExeFile = new File(path + "/fbx-conv.exe");
            if (!fbxConvExeFile.exists()) {
                try {
                    FileUtils.copyURLToFile(classLoader.getResource("fbx-conv/windows/fbx-conv.exe"), fbxConvExeFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error during copy  fbx-conv.exe");
                    System.exit(0);
                }
            }
        }
        else if (Main.detectedOS == Main.OSType.Linux){
            File fbxConvExeFile = new File(path + "/fbx-conv");
            File binary = new File(path + "libfbxsdk.so");
            if (!fbxConvExeFile.exists()) {
                try {
                    FileUtils.copyURLToFile(classLoader.getResource("fbx-conv/linux/fbx-conv"), fbxConvExeFile);
                    FileUtils.copyURLToFile(classLoader.getResource("fbx-conv/linux/libfbxsdk.so"), binary);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error during copy  fbx-conv files on linux");
                    System.exit(0);
                }
            }
        }    else if (Main.detectedOS == Main.OSType.MacOS){
            File fbxConvExeFile = new File(path + "/fbx-conv");
            File binary = new File(path + "libfbxsdk.dylib");
            if (!fbxConvExeFile.exists()) {
                try {
                    FileUtils.copyURLToFile(classLoader.getResource("fbx-conv/macos/fbx-conv.exe"), fbxConvExeFile);
                    FileUtils.copyURLToFile(classLoader.getResource("fbx-conv/macos/libfbxsdk.dylib"), binary);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error during copy  fbx-conv on macos");
                    System.exit(0);
                }
            }
        }
    }
}
