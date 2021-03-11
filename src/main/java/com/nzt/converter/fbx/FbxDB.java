package com.nzt.converter.fbx;

import com.nzt.converter.utils.Utils;
import com.nzt.converter.utils.WrapperConvertFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FbxDB {

    public HashMap<String, Long> mapFbxFiles;
    public HashMap<String, Long> mapTxtFiles;
    private String fbxFolderPath;

    private static String TXT_SEPARATOR = "###";
    private static String CHARSET = "UTF-8";


    public FbxDB(String fbxFolderPath) {
        this.fbxFolderPath = fbxFolderPath;
        mapFbxFiles = new HashMap<>();
        mapTxtFiles = new HashMap<>();
    }

    public List<WrapperConvertFile> compareFbxFilesAndTxtDB() {
        Path relativePath = Paths.get(fbxFolderPath);
        List<WrapperConvertFile> list = new ArrayList<>();
        for (Map.Entry<String, Long> fbxEntry : mapFbxFiles.entrySet()) {
            File fbxFile = new File(relativePath + "/" + fbxEntry.getKey());
            if (fbxFile.exists()) {
                boolean toConvert = true;
                Long timeInTxt = mapTxtFiles.get(fbxEntry.getKey());
                if (timeInTxt != null && timeInTxt.equals(fbxEntry.getValue())) {
                    toConvert = false;
                }
                WrapperConvertFile wrapperConvertFile = new WrapperConvertFile(toConvert, fbxEntry.getKey(), fbxEntry.getValue());
                list.add(wrapperConvertFile);
            }
        }
        return list;
    }

    public void rewriteTxtDb(List<WrapperConvertFile> toConvertList) {
        StringBuilder sb = new StringBuilder();
        for (WrapperConvertFile wrapperConvertFile : toConvertList) {
            sb.append(wrapperConvertFile.relativePath + TXT_SEPARATOR + wrapperConvertFile.lastModifiedTime + "\n");
        }
        File dbTxt = new File(fbxFolderPath + "/db.txt");
        try {
            FileUtils.writeStringToFile(dbTxt, sb.toString(), CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCsvDb() {
        try {
            File dbTxt = new File(fbxFolderPath + "/db.txt");
            List<String> lines = null;
            lines = FileUtils.readLines(dbTxt, CHARSET);
            for (String line : lines) {
                String[] split = line.split(TXT_SEPARATOR);
                mapTxtFiles.put(split[0], Long.valueOf(split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findAllFbxFiles() {
        Path relativePath = Paths.get(fbxFolderPath);
        try (Stream<Path> walk = Files.walk(relativePath)) {
            List<String> result = walk.filter(Files::isRegularFile).filter(f -> f.getFileName().toString().endsWith(".fbx"))
                    .map(x -> relativePath.relativize(x).toString()).collect(Collectors.toList());

            for (String f : result) {
                Path file = Paths.get(relativePath + "/" + f);
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                mapFbxFiles.put(f, attr.lastModifiedTime().toMillis());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
