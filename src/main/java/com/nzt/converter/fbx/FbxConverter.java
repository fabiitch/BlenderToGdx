package com.nzt.converter.fbx;

import com.nzt.converter.utils.Utils;
import com.nzt.converter.utils.WrapperConvertFile;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class FbxConverter {

    private Runtime rt;
    private String fbxFolderPath;
    private String g3dbPath;
    private String fbxExePath;

    private FbxDB fbxDB;

    public FbxConverter(String fbxFolderPath, String resultPath) {
        ClassLoader classLoader = getClass().getClassLoader();
        this.rt = Runtime.getRuntime();
        this.fbxExePath = Utils.replacePath(classLoader.getResource("fbx-conv.exe").getPath());
        this.fbxFolderPath = Utils.replacePath(fbxFolderPath);
        this.g3dbPath = Utils.replacePath(resultPath);
    }

    public void readDbAndConvertAll() {
        this.fbxDB = new FbxDB(fbxFolderPath);
        this.fbxDB.findAllFbxFiles();
        this.fbxDB.readCsvDb();

        List<WrapperConvertFile> wrapperConvertFiles = this.fbxDB.compareFbxFilesAndTxtDB();
        List<WrapperConvertFile> toConvertList = wrapperConvertFiles.stream().filter(w -> w.toConvert).collect(Collectors.toList());

        for (WrapperConvertFile file : toConvertList) {
            String folderPath = FilenameUtils.getFullPathNoEndSeparator(file.relativePath);
            Utils.createAllFolderForPath(g3dbPath, folderPath);
            convertFile(file.relativePath);
        }
        fbxDB.rewriteTxtDb(toConvertList);
    }

    public void convertFile(String filePath) {

        filePath = FilenameUtils.removeExtension(filePath);
        String fileFbxPath = (fbxFolderPath + "/" + filePath + ".fbx").replace("\\", File.separator);
        String convertedPath = (g3dbPath + "/" + filePath + ".g3db").replace("\\", File.separator);
        try {
            Process exec = null;
            exec = rt.exec(fbxExePath + " -f -v " + fileFbxPath + " " + convertedPath);

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
