package com.nzt.converter.fbx;

import com.nzt.converter.Main;
import com.nzt.converter.utils.OSType;
import com.nzt.converter.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FbxCopyResourcesTest {

    @Test
    public void copyFbxExeFiles() {
        ClassLoader classLoader = getClass().getClassLoader();
        String copySrc = classLoader.getResource("fbx/copyExe").getPath();
        FbxCopyResources fbxCopyResources = new FbxCopyResources();
        fbxCopyResources.copyResourcesFiles(copySrc);
        fbxCopyResources.copyGitIngnore(copySrc);

        String gitIgnorePath = Utils.replacePath(classLoader.getResource("fbx/copyExe/BlenderToGdxConfig/.gitignore").getPath());
        Assertions.assertTrue(new File(gitIgnorePath).exists());

        if (Main.detectedOS == OSType.Windows) {
            String exeCopyPath = Utils.replacePath(classLoader.getResource("fbx/copyExe/BlenderToGdxConfig/fbx-conv.exe").getPath());
            Assertions.assertTrue(new File(exeCopyPath).exists());
        }

        if (Main.detectedOS == OSType.Linux) {
            String exeCopyPath = Utils.replacePath(classLoader.getResource("fbx/copyExe/BlenderToGdxConfig/fbx-conv").getPath());
            Assertions.assertTrue(new File(exeCopyPath).exists());

            String binaryCopyPath = Utils.replacePath(classLoader.getResource("fbx/copyExe/BlenderToGdxConfig/libfbxsdk.so").getPath());
            Assertions.assertTrue(new File(binaryCopyPath).exists());
        }

        if (Main.detectedOS == OSType.MacOS) {
            String exeCopyPath = Utils.replacePath(classLoader.getResource("fbx/copyExe/BlenderToGdxConfig/fbx-conv").getPath());
            Assertions.assertTrue(new File(exeCopyPath).exists());

            String binaryCopyPath = Utils.replacePath(classLoader.getResource("fbx/copyExe/BlenderToGdxConfig/libfbxsdk.dylib").getPath());
            Assertions.assertTrue(new File(binaryCopyPath).exists());
        }
    }
}
