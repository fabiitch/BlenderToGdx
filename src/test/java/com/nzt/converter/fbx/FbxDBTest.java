package com.nzt.converter.fbx;

import org.junit.jupiter.api.Test;

public class FbxDBTest {
    @Test
    public void testReadDbTxt(){
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = classLoader.getResource("fbx/fbxFiles").getPath();
        FbxDB fbxDB = new FbxDB(fbxFolderPath);
        fbxDB.initTxtDb();
    }
    @Test
    public void testFindFbxFiles(){
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = classLoader.getResource("fbx/fbxFiles").getPath();
        final String fbxResultPath = classLoader.getResource("fbx/resultFiles").getPath();

        FbxDB fbxDB = new FbxDB(fbxFolderPath);
        fbxDB.initFbx();
    }

}
