package com.nzt.converter.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    public void testReplacePathFbx() {
        String fakePath = "toto/titi\\tata/kk\\toto.fbx";

        String replace = Utils.replacePathForFbx(fakePath);
        Assertions.assertEquals("toto/titi/tata/kk/toto.fbx", replace);
    }

    @Test
    public void testCreateFolder() {
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = Utils.replacePath(classLoader.getResource("fbx").getPath());
        String folderPath = "create/folder/test";
        boolean ok = Utils.createAllFolderForPath(fbxFolderPath, folderPath);
        Assertions.assertTrue(ok);
        String path = classLoader.getResource("fbx/create/folder/test").getPath();
        Assertions.assertNotNull(path);
    }
}
