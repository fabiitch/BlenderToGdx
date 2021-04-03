package com.nzt.converter.fbx;

import com.nzt.converter.Main;
import com.nzt.converter.utils.OSType;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FbxCopyResources {

    public void copyGitIngnore(String path){
        ClassLoader classLoader = getClass().getClassLoader();
        File gitIngoreFile = new File(path + Main.BLENDER_TO_GDX_FOLDER_CONF + "/.gitignore");
        try {
            FileUtils.copyURLToFile(classLoader.getResource("gitIgnore"), gitIngoreFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error during copy  .gitIgnore");
            System.exit(0);
        }
        gitIngoreFile.setReadable(true, false);
        gitIngoreFile.setExecutable(true);
    }
    public void copyResourcesFiles(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        if (Main.detectedOS == OSType.Windows) {
            File fbxConvExeFile = new File(path + Main.BLENDER_TO_GDX_FOLDER_CONF + "/fbx-conv.exe");
            if (!fbxConvExeFile.exists()) {
                try {
                    FileUtils.copyURLToFile(classLoader.getResource("fbx-conv/windows/fbx-conv.exe"), fbxConvExeFile);
                    fbxConvExeFile.setReadable(true, false);
                    fbxConvExeFile.setExecutable(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error during copy  fbx-conv.exe");
                    System.exit(0);
                }
            }
        } else if (Main.detectedOS == OSType.Linux) {
            File fbxConvExeFile = new File(path + Main.BLENDER_TO_GDX_FOLDER_CONF + "/fbx-conv");
            File binary = new File(path + Main.BLENDER_TO_GDX_FOLDER_CONF + "/libfbxsdk.so");
            if (!fbxConvExeFile.exists() || !binary.exists()) {
                try {
                    FileUtils.copyURLToFile(classLoader.getResource("fbx-conv/linux/fbx-conv"), fbxConvExeFile);
                    if(fbxConvExeFile.exists()){
                        fbxConvExeFile.setReadable(true, false);
                        fbxConvExeFile.setExecutable(true);
                        binary.setReadable(true,false);
                        binary.setExecutable(true);
                    }
                    FileUtils.copyURLToFile(classLoader.getResource("fbx-conv/linux/libfbxsdk.so"), binary);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error during copy  fbx-conv files on linux");
                    System.exit(0);
                }
                givePermissionToBlenderToGdx(path);
            }
        } else if (Main.detectedOS == OSType.MacOS) {
            File fbxConvExeFile = new File(path + Main.BLENDER_TO_GDX_FOLDER_CONF + "/fbx-conv");
            File binary = new File(path + Main.BLENDER_TO_GDX_FOLDER_CONF + "/libfbxsdk.dylib");
            if (!fbxConvExeFile.exists() || !binary.exists()) {
                try {
                    FileUtils.copyURLToFile(classLoader.getResource("fbx-conv/macos/fbx-conv.exe"), fbxConvExeFile);
                    FileUtils.copyURLToFile(classLoader.getResource("fbx-conv/macos/libfbxsdk.dylib"), binary);
                    fbxConvExeFile.setReadable(true, false);
                    fbxConvExeFile.setExecutable(true);
                    binary.setReadable(true,false);
                    binary.setExecutable(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error during copy  fbx-conv on macos");
                    System.exit(0);
                }
                givePermissionToBlenderToGdx(path);
            }
        }
    }


    public void givePermissionToBlenderToGdx(String fbxFolderPath) {
        Runtime rt = Runtime.getRuntime();
        try {
            Process exec = rt.exec("chmod 777 " + fbxFolderPath + Main.BLENDER_TO_GDX_FOLDER_CONF + "/*");
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(exec.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(exec.getErrorStream()));

            // Read the output from the command
            System.out.println("Permission to BlenderToGdx folder: " + fbxFolderPath + Main.BLENDER_TO_GDX_FOLDER_CONF);
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println("=====================");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
