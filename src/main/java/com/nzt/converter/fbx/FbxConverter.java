package com.nzt.converter.fbx;

import com.nzt.converter.Main;
import com.nzt.converter.utils.ConvertFileWrapper;
import com.nzt.converter.utils.CsvDb;
import com.nzt.converter.utils.OSType;
import com.nzt.converter.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class FbxConverter {
    private static String CSV_FILE_NAME = "fbx-to-gdx-db.csv";
    private Runtime rt;
    private String fbxExePath;

    private CsvDb csvDb;
    private FbxConverterOptions options;

    public FbxConverter(FbxConverterOptions options) {
        this.options = options;
        this.rt = Runtime.getRuntime();

        String exeName = "fbx-conv";
        if (Main.detectedOS == OSType.Windows) {
            exeName += ".exe";
        }
        File fbxConvExeFile = new File(options.fbxFolderPath + Main.BLENDER_TO_GDX_FOLDER_CONF + "/" + exeName);
        if (!fbxConvExeFile.exists()) {
            FbxCopyResources copyResources = new FbxCopyResources();
            copyResources.copyResourcesFiles(options.fbxFolderPath);
        }
        this.fbxExePath = Utils.replacePathForFbx(fbxConvExeFile.getAbsolutePath());
        this.csvDb = new CsvDb(options.fbxFolderPath, CSV_FILE_NAME);

        System.out.println("============ Init =============");
        System.out.println("fbxFolderPath : " + options.fbxFolderPath);
        System.out.println("exportFolderPath : " + options.exportFolderPath);
        System.out.println("fbx-conv.exe : " + fbxConvExeFile.getAbsolutePath());
    }

    public void readDbAndConvertAll() {
        csvDb.read();
        List<ConvertFileWrapper> wrapperConvertFiles = csvDb.compareWithExisting(".fbx");
        List<ConvertFileWrapper> toConvertList = wrapperConvertFiles.stream().filter(w -> w.toConvert).collect(Collectors.toList());
        System.out.println(toConvertList.size() + " / " + wrapperConvertFiles.size() + " files to convert");
        for (ConvertFileWrapper file : toConvertList) {
            String folderPath = FilenameUtils.getFullPathNoEndSeparator(file.relativePath);
            Utils.createAllFolderForPath(options.exportFolderPath, folderPath);
            convertFile(file.relativePath);
        }
        csvDb.write(wrapperConvertFiles);
    }


    public void convertFile(String filePath) {
        filePath = FilenameUtils.removeExtension(filePath);
        String commandArgs = fbxExePath + " ";
        if (options.flipVTextureCoordinates) {
            commandArgs += "-f ";
        }
        if (options.packVertexColors)
            commandArgs += "-p ";
        if (options.maxMeshVertices != null)
            commandArgs += "-m " + options.maxMeshVertices + " ";
        if (options.maxBonesVertex != null)
            commandArgs += "-b " + options.maxBonesVertex + " ";
        if (options.maxBonesVertex != null)
            commandArgs += "-w " + options.maxBonesVertex + " ";
        if (options.verbose)
            commandArgs += "-v ";


        String fileFbxPath = (options.fbxFolderPath + "/" + filePath + ".fbx").replace("\\", File.separator);
        String convertedPath = (options.exportFolderPath + "/" + filePath + ".g3db").replace("\\", File.separator);
        try {
            Process exec = null;
            exec = rt.exec(commandArgs + " " + fileFbxPath + " " + convertedPath);

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
