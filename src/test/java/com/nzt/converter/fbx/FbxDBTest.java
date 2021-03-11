package com.nzt.converter.fbx;

import com.nzt.converter.utils.Utils;
import com.nzt.converter.utils.WrapperConvertFile;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class FbxDBTest {
    @Test
    public void testReadDbTxt() {
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = classLoader.getResource("fbx/simpleTest").getPath();
        FbxDB fbxDB = new FbxDB(Utils.replacePath(fbxFolderPath));
        fbxDB.readCsvDb();
        Assertions.assertEquals(2, fbxDB.mapTxtFiles.size());
    }

    @Test
    public void testFindFbxFiles() {
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = classLoader.getResource("fbx/fbxFiles").getPath();

        FbxDB fbxDB = new FbxDB(Utils.replacePath(fbxFolderPath));
        fbxDB.findAllFbxFiles();
        Assertions.assertEquals(4, fbxDB.mapFbxFiles.size());
    }

    @Test
    @Ignore
    public void testCompareFbxAndDBTxt() {
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = classLoader.getResource("fbx/fbxFiles").getPath();
        FbxDB fbxDB = new FbxDB(Utils.replacePath(fbxFolderPath));
        fbxDB.findAllFbxFiles();
        fbxDB.readCsvDb();

        List<WrapperConvertFile> wrapperConvertFiles = fbxDB.compareFbxFilesAndTxtDB();
        List<WrapperConvertFile> listToConvert = wrapperConvertFiles.stream().filter(w -> w.toConvert).collect(Collectors.toList());
        List<WrapperConvertFile> listToNotConvert = wrapperConvertFiles.stream().filter(w -> !w.toConvert).collect(Collectors.toList());

        Assertions.assertEquals(2, listToConvert.size());
        Assertions.assertEquals(1, listToNotConvert.size());
    }
}
