package com.nzt.converter.fbx;

import com.nzt.converter.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FbxDB {

    private HashMap<String, Long> mapFbxFiles;
    private HashMap<String, Long> mapTxtFiles;
    private String fbxFolderPath;

    public FbxDB(String fbxFolderPath) {
        this.fbxFolderPath = Utils.replacePath(fbxFolderPath);
        mapFbxFiles = new HashMap<>();
        mapTxtFiles = new HashMap<>();


    }

    public void compareFbxAndDB(){
        
    }

    public void initTxtDb() {
        try {
            File f = new File(fbxFolderPath + "/db.txt");
            List<String> lines = null;
            lines = FileUtils.readLines(f, "UTF-8");
            for (String line : lines) {
                String[] split = line.split("###");
                mapTxtFiles.put(split[0], Long.valueOf(split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initFbx() {
        try (Stream<Path> walk = Files.walk(Paths.get(fbxFolderPath))) {
            List<String> result = walk.filter(Files::isRegularFile).filter(f -> f.getFileName().toString().endsWith(".fbx"))
                    .map(x -> x.toString()).collect(Collectors.toList());

            for (String f : result) {
                Path file = Paths.get(f);
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                mapFbxFiles.put(f, attr.lastModifiedTime().toMillis());
            }

            System.out.println("toto");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
