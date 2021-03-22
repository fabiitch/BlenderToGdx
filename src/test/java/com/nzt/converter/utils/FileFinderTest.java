package com.nzt.converter.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class FileFinderTest {

    @Test
    public void testFindFbxFiles() {
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = classLoader.getResource("fbx/fullTest").getPath();

        HashMap<String, String> fbxFiles = FileFinder.findAllFiles(fbxFolderPath, ".fbx");
        Assertions.assertEquals(4, fbxFiles.size());
    }
}
