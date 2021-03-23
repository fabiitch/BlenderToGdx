package com.nzt.converter.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class FileFinderTest {

    @Test
    public void testFindFbxFiles() {
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = classLoader.getResource("fbx/fullTest").getPath();

        HashMap<String, String> fbxFiles = FileFinder.findAllFiles(fbxFolderPath, ".fbx");

        for (Map.Entry<String, String> stringStringEntry : fbxFiles.entrySet()) {
            System.out.println("=====");
            System.out.println(stringStringEntry.getKey());
            System.out.println(stringStringEntry.getValue());
        }

        Assertions.assertEquals(4, fbxFiles.size());
    }
}
