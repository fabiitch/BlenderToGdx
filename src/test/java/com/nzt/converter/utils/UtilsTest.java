package com.nzt.converter.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UtilsTest {

    @Test
    public void testReplacePathFbx() {
        String fakePath = "toto/titi\\tata/kk\\toto.fbx";

        String replace = Utils.replacePathForFbx(fakePath);
        Assertions.assertEquals("toto/titi/tata/kk/toto.fbx", replace);
    }

    @Test
    public void testCreateFolder() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        Path fbxFolderPath = Paths.get(classLoader.getResource("fbx").toURI());
        String folderPath = "create/folder/test";
        boolean ok = Utils.createAllFolderForPath(fbxFolderPath, folderPath);
        Assertions.assertTrue(ok);
        String path = classLoader.getResource("fbx/create/folder/test").getPath();
        Assertions.assertNotNull(path);
    }
}
