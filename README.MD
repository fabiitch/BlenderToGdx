# Blender to Gdx
[![Build](https://github.com/fabiitch/BlenderToGdx/workflows/Java%20CI/badge.svg?branch=master)](https://github.com/fabiitch/BlenderToGdx/actions/workflows/gradle.yml) (Java CI)
[![Downloads](https://img.shields.io/github/downloads/fabiitch/BlenderToGdx/total.svg)](https://github.com/fabiitch/BlenderToGdx/releases)

### Step 1 : Blender to Fbx format => not impl
### Step 2 : Fbx to G3DB =>  impl
****
### Why
When u modify a 3D model in blender, 
1) You have to export it as fbx
2) Use fbx-conv.exe for convert as g3db/g3dj 

The goal of this project is to do this 2 step automatically at each build.gradle for all files.
****
### Configuration
For fbx to libGDX, you have to create a properties file for export options, [example properties file](src/test/resources/fbx/fullTest/fbx-to-gdx.properties)
The properties file have to be in same folder than the BlenderToGdx.jar
The required properties are 'fbxFolderPath' (folder who contains all .fbx) and 'exportFolderPath' (generally your assets/models folder)
****
### How to use ?
1) Use jar directly java -jar blenderToGdx
2) Add to your gradle.build
3) Modify source to set your path directly and rexport as jar

****
### How its work ? (example for fbx->G3DB/G3DJ) 
1) Scan the entry directory and list all .fbx
2) Convert all fbx files to .G3DB/G3DJ and keep file name and tree structure
3) Write in a CSV file([fbx-convert-db.csv](src/test/resources/fbx/fullTest/fbx-to-gdx-db.csv) the path and last modification date of the .fbx
4) Next execution will look the last modification date of the .fbx and compare with CSV to check if the file need to be reexported
