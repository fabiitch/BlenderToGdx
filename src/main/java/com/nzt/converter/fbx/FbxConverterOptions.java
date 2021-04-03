package com.nzt.converter.fbx;

import com.nzt.converter.utils.Utils;

import java.io.File;
import java.nio.file.Path;
import java.util.Properties;

public class FbxConverterOptions {

    public Path jarPath; //path of jar location
    public Path fbxFolderPath;
    public Path exportFolderPath;

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

    public FbxConverterOptions(Path jarPath, Path fbxFolderPath, Path exportFolderPath) {
        this.jarPath = jarPath;
        this.fbxFolderPath = fbxFolderPath;
        this.exportFolderPath = exportFolderPath;
    }

    public FbxConverterOptions(Path jarPath, Properties properties) {
        this.jarPath = jarPath;
        if (properties.getProperty("fbxFolderPath") != null) {
            this.fbxFolderPath = new File(jarPath.toString() + properties.getProperty("fbxFolderPath")).toPath();
        } else {
            System.err.println("FbxFolderPath s required\"");
            System.exit(0);
        }
        if (properties.getProperty("exportFolderPath") != null) {
            this.exportFolderPath = new File(jarPath.toString() + properties.getProperty("exportFolderPath")).toPath();
        } else {
            System.err.println("exportFolderPath is required");
            System.exit(0);
        }

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
