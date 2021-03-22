package com.nzt.converter.fbx;

import com.nzt.converter.Main;
import com.nzt.converter.utils.ConvertFileWrapper;
import com.nzt.converter.utils.FileFinder;
import com.nzt.converter.utils.csv.CompareFolderAndDB;
import com.nzt.converter.utils.csv.CsvDb;
import com.nzt.converter.utils.OSType;
import com.nzt.converter.utils.Utils;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
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
        String userDir = System.getProperty("user.dir");
        File fbxConvExeFile = new File(userDir + Main.BLENDER_TO_GDX_FOLDER_CONF + "/" + exeName);
        if (!fbxConvExeFile.exists()) {
            FbxCopyResources copyResources = new FbxCopyResources();
            copyResources.copyResourcesFiles(userDir);
        }
        this.fbxExePath = Utils.replacePathForFbx(fbxConvExeFile.getAbsolutePath());
        this.csvDb = new CsvDb(userDir, Main.BLENDER_TO_GDX_FOLDER_CONF + "/" + CSV_FILE_NAME);

        System.out.println("fbxFolderPath : " + options.fbxFolderPath);
        System.out.println("exportFolderPath : " + options.exportFolderPath);
        System.out.println("fbx-conv.exe : " + fbxConvExeFile.getAbsolutePath());
        System.out.println("============ End Init =============");
    }

    public void readDbAndConvertAll() {
        System.out.println("");
        System.out.println("===== Read CSV DB ========");
        HashMap<String, String> csvValues = csvDb.read();
        HashMap<String, String> filesInFolder = FileFinder.findAllFiles(options.fbxFolderPath, ".fbx");
        System.out.println("Found " + csvValues.size() + " entry in csv Files");
        System.out.println("Found " + filesInFolder.size() + " files .fbx in FbxFolder");
        List<ConvertFileWrapper> wrapperConvertFiles = CompareFolderAndDB.compare(filesInFolder, csvValues);

        List<ConvertFileWrapper> toConvertList = wrapperConvertFiles.stream().filter(w -> w.toConvert).collect(Collectors.toList());
        System.out.println(toConvertList.size() + " / " + wrapperConvertFiles.size() + " files to convert");
        System.out.println("==== END  compareWithExisting ====");

        System.err.println("======= Start Conversion Files:");
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
