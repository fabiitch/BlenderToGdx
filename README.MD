# Blender to Gdx
###Step 1 : Blender to Fbx format => not impl
###Step 2 : Fbx to G3DB =>  impl
****
### How to use ?
1) Use jar directly java -jar "fbxFolderPath" "assetsPath"
2) Add to your gradle.build
3) Modify source to set your path directly and rexport as jar
****
### How its work ?(example for fbx->G3DB/G3DJ) 
1) Scan the entry directory and list all .fbx
2) Convert all file to .G3DB/G3DJ and keep file name and tree structure
3) Write in a CSV file the path and last modification date of the .fbx
4) Next execution will look the last modification date of the .fbx and compare with CSV to check if the file need to be reexported
