package com.nzt.converter.fbx;

import com.nzt.converter.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FbxConverter {

    private Runtime rt;
    private boolean binary = true;
    private String fbxFilesPath;
    private String resultPath;
    private String fbxExePath;

    public FbxConverter(String fbxFilesPath, String resultPath) {
        ClassLoader classLoader = getClass().getClassLoader();
        rt = Runtime.getRuntime();
        this.fbxExePath = Utils.replacePath(classLoader.getResource("fbx-conv.exe").getPath());
        this.fbxFilesPath = Utils.replacePath(fbxFilesPath);
        this.resultPath = Utils.replacePath(resultPath);
    }

    public void convert(String fileName) {
        String fileFbxPath = (fbxFilesPath + "/" + fileName + ".fbx").replace("\\", File.separator);
        String convertedPath = (resultPath + "/" + fileName + ".g3db").replace("\\", File.separator);
        try {
            Process exec = null;
            if (binary) {
                exec = rt.exec(fbxExePath + " -f -v " + fileFbxPath+" " + convertedPath);
            } else {
            }

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(exec.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(exec.getErrorStream()));

            // Read the output from the command
            System.out.println("Fbx convert file : " + fileName);
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
