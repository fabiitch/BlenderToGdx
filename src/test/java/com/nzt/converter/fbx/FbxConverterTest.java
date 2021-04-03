package com.nzt.converter.fbx;

import com.nzt.converter.Main;
import com.nzt.converter.utils.FileFinder;
import com.nzt.converter.utils.OSType;
import com.nzt.converter.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class FbxConverterTest {

    @Test
    public void convertSimpleTest() throws URISyntaxException {
        if (Main.detectedOS != OSType.Windows) {
            return;
        }
        ClassLoader classLoader = getClass().getClassLoader();
        Path fbxFolderPath = Paths.get(classLoader.getResource("fbx/simpleTest").toURI());

        FbxConverterOptions options = new FbxConverterOptions(fbxFolderPath, fbxFolderPath, fbxFolderPath);
        FbxConverter fbxConverter = new FbxConverter(options);
        fbxConverter.convertFile("playerCube.fbx");

        String pathResultConverted = Utils.replacePath(classLoader.getResource("fbx/simpleTest/playerCube.g3db").getPath());
        Assertions.assertTrue(new File(pathResultConverted).exists());
    }

    @Test
    public void convertFullTest() throws URISyntaxException {
        if (Main.detectedOS != OSType.Windows) {
            return;
        }
        ClassLoader classLoader = getClass().getClassLoader();
        Path fbxFolderPath = Paths.get(classLoader.getResource("fbx/fullTest").toURI());
        Path exportFolderPath = Paths.get(classLoader.getResource("fbx/fullTest/g3db").toURI());

        FbxConverterOptions options = new FbxConverterOptions(fbxFolderPath, fbxFolderPath, exportFolderPath);

        FbxConverter fbxConverter = new FbxConverter(options);
        fbxConverter.readDbAndConvertAll();
        Assertions.assertNotNull(classLoader.getResource("fbx/fullTest/g3db/playerCube.g3db"));
        Assertions.assertNotNull(classLoader.getResource("fbx/fullTest/g3db/player/player.g3db"));
        Assertions.assertNotNull(classLoader.getResource("fbx/fullTest/g3db/player/skins/playerSkin1.g3db"));
        Assertions.assertNotNull(classLoader.getResource("fbx/fullTest/g3db/player/skins/playerSkin2.g3db"));

        HashMap<String, String> fbxResultPath1 = FileFinder.findAllFiles(fbxFolderPath, ".fbx");
        System.err.println("================");
        System.err.println("================");
        System.err.println("Second convert");
        fbxConverter.readDbAndConvertAll();
        HashMap<String, String> fbxResultPath2 = FileFinder.findAllFiles(fbxFolderPath, ".fbx");


        Assertions.assertTrue(fbxResultPath1.equals(fbxResultPath2));
    }
}
