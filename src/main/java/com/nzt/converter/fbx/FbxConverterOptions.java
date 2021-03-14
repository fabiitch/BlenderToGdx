package com.nzt.converter.fbx;

import com.nzt.converter.utils.Utils;

import java.util.Properties;

public class FbxConverterOptions {

    public String fbxFolderPath;
    public String exportFolderPath;
    public boolean isRelativePath;

    //G3DJ (json) or G3DB (binary).
    public boolean binary = true;

    //Flip the V texture coordinates.
    public boolean flipVTextureCoordinates = true;

    //Pack vertex colors to one float.
    public boolean packVertexColors = false;

    //The maximum amount of vertices or indices a mesh may contain (default: 32k)
    public Long maxMeshVertices;

    //The maximum amount of bones a nodepart can contain (default: 12)
    public Long maxBonesNode;

    //The maximum amount of bone weights per vertex (default: 4)
    public Long maxBonesVertex;

    //Verbose: print additional progress information
    public boolean verbose = false;

    public FbxConverterOptions(String fbxFolderPath, String exportFolderPath) {
        this.fbxFolderPath = Utils.replacePath(fbxFolderPath);
        this.exportFolderPath = Utils.replacePath(exportFolderPath);
    }

    public FbxConverterOptions(Properties properties) {
        if (properties.getProperty("isRelativePath") != null) {
            this.isRelativePath = Boolean.parseBoolean(properties.getProperty("isRelativePath"));
        }
        if (properties.getProperty("fbxFolderPath") != null) {
            this.fbxFolderPath = properties.getProperty("fbxFolderPath");
        } else {
            System.out.println("FbxFolderPath s required\"");
            System.exit(0);
        }
        if (properties.getProperty("exportFolderPath") != null) {
            this.exportFolderPath = properties.getProperty("exportFolderPath");
        } else {
            System.out.println("exportFolderPath is required");
            System.exit(0);
        }
        if (isRelativePath) {
            String initPath = System.getProperty("user.dir");
            this.fbxFolderPath = initPath  + fbxFolderPath;
            this.exportFolderPath = initPath + exportFolderPath;
        }
        this.fbxFolderPath = Utils.replacePath(fbxFolderPath);
        this.exportFolderPath = Utils.replacePath(exportFolderPath);


        if (properties.getProperty("binary") != null)
            this.binary = Boolean.parseBoolean(properties.getProperty("binary"));

        if (properties.getProperty("flipVTextureCoordinates") != null)
            this.flipVTextureCoordinates = Boolean.parseBoolean(properties.getProperty("flipVTextureCoordinates"));

        if (properties.getProperty("packVertexColors") != null)
            this.packVertexColors = Boolean.parseBoolean(properties.getProperty("packVertexColors"));

        if (properties.getProperty("maxMeshVertices") != null)
            this.maxMeshVertices = Long.valueOf(properties.getProperty("maxMeshVertices"));

        if (properties.getProperty("maxBonesNode") != null)
            this.maxBonesNode = Long.valueOf(properties.getProperty("maxBonesNode"));

        if (properties.getProperty("maxBonesVertex") != null)
            this.maxBonesVertex = Long.valueOf(properties.getProperty("maxBonesVertex"));

        if (properties.getProperty("verbose") != null)
            this.verbose = Boolean.parseBoolean(properties.getProperty("verbose"));
    }
}
