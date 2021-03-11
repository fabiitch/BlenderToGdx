package com.nzt.converter.fbx;

import com.nzt.converter.fbx.FbxConverter;
import com.nzt.converter.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FbxConverterTest {

    @Test
    public void simpleTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = classLoader.getResource("fbx/simpleTest").getPath();
        final String fbxResultPath = classLoader.getResource("fbx/simpleTest").getPath();

        FbxConverter fbxConverter = new FbxConverter(fbxFolderPath, fbxResultPath);

        fbxConverter.convert("playerCube");

        String pathResultConverted = Utils.replacePath(classLoader.getResource("fbx/simpleTest/playerCube.g3db").getPath());

        Assertions.assertTrue(new File(pathResultConverted).exists());
    }


    public void fullTest(){
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFilesPath = classLoader.getResource("fbx/fbxFiles").getPath();
        final String fbxResultPath = classLoader.getResource("fbx/resultFiles").getPath();
    }
}
