# Blender to Gdx
[![Build](https://github.com/fabiitch/BlenderToGdx/workflows/Java%20CI/badge.svg?branch=master)](https://github.com/fabiitch/BlenderToGdx/actions/workflows/gradle.yml)
[![Downloads](https://img.shields.io/github/downloads/fabiitch/BlenderToGdx/total.svg)](https://github.com/fabiitch/BlenderToGdx/releases)

### Step 1 : Blender to Fbx format => not impl
### Step 2 : Fbx to G3DB =>  impl
#### 
****
### Why ?
When u modify a 3D model in blender, 
1) You have to export it as fbx
2) Use fbx-conv.exe for convert as g3db/g3dj 

The goal of this project is to do this 2 step automatically at each build.gradle for all files.
****
### Configuration
(linux and macos) set up environment for binary lib see [fbx-conv](https://github.com/libgdx/fbx-conv)
1) Download jar
2) Put it in your favorite folder
3) Create folder BlendertoGdx at same level
4) Inside BlendertoGdx, create a properties file for export options, [example properties file](src/test/resources/fbx/fullTest/BlenderToGdx/fbx-to-gdx.properties)
5) Configure properties (example non binary export, flip texture, [look differents option](https://github.com/libgdx/fbx-conv))
6) The required properties are 'fbxFolderPath' (folder who contains all .fbx) and 'exportFolderPath' (generally your assets/models folder)
7) The paths should be relative to jar position
****
### How to use ?
1) Use jar directly java -jar BlenderToGdx
2) Add to your core/build.gradle 
   ```
   processResources.dependsOn('blenderToGdx')
   task blenderToGdx(type: JavaExec) {
    group 'other'
    description 'Execute BlenderToGdx jar'
    classpath = files('../blender/BlenderToGdx.jar') //jar relative Path
    }
   ```
3) Modify source to set your path directly and re-export as jar

You can find example in [test/resources](src/test/resources/fbx/fullTest)

****
### How its work ? 
##### Example for .fbx -> .G3DB/.G3DJ
1) Search the [properties files](src/test/resources/fbx/fullTest/BlenderToGdx/fbx-to-gdx.properties) for export options
2) Scan the entry directory and list all .fbx
3) Copy fbx-conv.exe from resource to BlenderToGdx   
4) Convert all fbx files to .G3DB/G3DJ and keep file name and tree structure
5) Write in a CSV file([fbx-convert-db.csv](src/test/resources/fbx/fullTest/BlenderToGdx/fbx-to-gdx-db.csv) the path and last modification date of the .fbx
6) Next execution will look the last modification date of the .fbx and compare with CSV to check if the file need to be reexported
7) a .gitignore file is created for exclude fbx-conv exe and fbx-convert-db.csv
