package com.nzt.converter.fbx;

import com.nzt.converter.utils.ConvertFileWrapper;
import com.nzt.converter.utils.CsvDb;
import com.nzt.converter.utils.Utils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class FbxConverter {
    private static String CSV_FILE_NAME = "fbx-convert-db.csv";
    private Runtime rt;
    private String fbxFolderPath;
    private String g3dbPath;
    private String fbxExePath;

    private CsvDb csvDb;

    public FbxConverter(String fbxFolderPath, String resultPath) {
        ClassLoader classLoader = getClass().getClassLoader();
        this.rt = Runtime.getRuntime();
        try {
            this.fbxExePath = Utils.replacePath(IOUtils.toString(classLoader.getResourceAsStream("fbx-conv.exe"), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.fbxFolderPath = Utils.replacePath(fbxFolderPath);
        this.g3dbPath = Utils.replacePath(resultPath);
        this.csvDb = new CsvDb(fbxFolderPath,CSV_FILE_NAME);
    }

    public void readDbAndConvertAll() {
        csvDb.read();

        List<ConvertFileWrapper> wrapperConvertFiles = csvDb.compareWithExisting(".txt");
        List<ConvertFileWrapper> toConvertList = wrapperConvertFiles.stream().filter(w -> w.toConvert).collect(Collectors.toList());

        for (ConvertFileWrapper file : toConvertList) {
            String folderPath = FilenameUtils.getFullPathNoEndSeparator(file.relativePath);
            Utils.createAllFolderForPath(g3dbPath, folderPath);
            convertFile(file.relativePath);
        }
        csvDb.write(toConvertList);
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
