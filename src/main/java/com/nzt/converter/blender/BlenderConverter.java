package com.nzt.converter.blender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BlenderConverter {

    private Runtime rt;
    private String blenderExePath;
    private String fbxFolderPath;
    private String blenderFolderPath;

    public BlenderConverter(String blenderExePath, String blenderFolderPath, String fbxFolderPath) {
        this.blenderExePath = blenderExePath;
        this.blenderFolderPath = blenderFolderPath;
        this.fbxFolderPath = fbxFolderPath;
    }

    public void convertFile(String filePath){

        String blenderCommand = "blender -b -o";
        try {
            Process exec = null;
//            exec = rt.exec(fbxExePath + " -f -v " + fileFbxPath + " " + convertedPath);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(exec.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(exec.getErrorStream()));

            // Read the output from the command
            System.out.println("Fbx convert file : " + filePath);
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
