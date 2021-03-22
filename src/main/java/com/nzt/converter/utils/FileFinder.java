package com.nzt.converter.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileFinder {

    /*
    return map<RelativePath,Lastmodified>
     */
    public static HashMap<String, String> findAllFiles(String folderPathStart, String extensionFiles) {
        HashMap<String, String> mapFbxFiles = new HashMap<>();
        Path relativePath = Paths.get(Utils.replacePath(folderPathStart));
        try (Stream<Path> walk = Files.walk(relativePath)) {
            List<String> result = walk.filter(Files::isRegularFile).filter(f -> f.getFileName().toString().endsWith(extensionFiles))
                    .map(x -> relativePath.relativize(x).toString()).collect(Collectors.toList());

            for (String f : result) {
                Path file = Paths.get(relativePath + "/" + f);
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                mapFbxFiles.put(f, attr.lastModifiedTime().toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapFbxFiles;
    }
}
