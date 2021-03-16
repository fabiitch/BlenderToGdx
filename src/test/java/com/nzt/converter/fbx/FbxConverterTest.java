package com.nzt.converter.fbx;

import com.nzt.converter.Main;
import com.nzt.converter.utils.FileFinder;
import com.nzt.converter.utils.OSType;
import com.nzt.converter.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

public class FbxConverterTest {

    @Test
    public void convertSimpleTest() {
        if (Main.detectedOS != OSType.Windows) {
            return;
        }
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = classLoader.getResource("fbx/simpleTest").getPath();
        final String fbxResultPath = classLoader.getResource("fbx/simpleTest").getPath();

        FbxConverterOptions options = new FbxConverterOptions(fbxFolderPath, fbxResultPath);
        FbxConverter fbxConverter = new FbxConverter(options);
        fbxConverter.convertFile("playerCube.fbx");

        String pathResultConverted = Utils.replacePath(classLoader.getResource("fbx/simpleTest/playerCube.g3db").getPath());
        Assertions.assertTrue(new File(pathResultConverted).exists());
    }

    @Test
    public void convertFullTest() {
        if (Main.detectedOS != OSType.Windows) {
            return;
        }
        ClassLoader classLoader = getClass().getClassLoader();
        String fbxFolderPath = classLoader.getResource("fbx/fullTest").getPath();
        String exportPath = classLoader.getResource("fbx/g3db").getPath();

        FbxConverterOptions options = new FbxConverterOptions(fbxFolderPath, exportPath);

        FbxConverter fbxConverter = new FbxConverter(options);
        fbxConverter.readDbAndConvertAll();
        Assertions.assertNotNull(classLoader.getResource("fbx/g3db/playerCube.g3db"));
        Assertions.assertNotNull(classLoader.getResource("fbx/g3db/player/player.g3db"));
        Assertions.assertNotNull(classLoader.getResource("fbx/g3db/player/skins/playerSkin1.g3db"));
        Assertions.assertNotNull(classLoader.getResource("fbx/g3db/player/skins/playerSkin2.g3db"));

        HashMap<String, Long> fbxResultPath1 = FileFinder.findAllFiles(exportPath, ".fbx");
        fbxConverter.readDbAndConvertAll();
        HashMap<String, Long> fbxResultPath2 = FileFinder.findAllFiles(exportPath, ".fbx");


        Assertions.assertTrue(fbxResultPath1.equals(fbxResultPath2));
    }
}
