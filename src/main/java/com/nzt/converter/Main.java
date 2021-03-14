package com.nzt.converter;

import com.nzt.converter.fbx.FbxConverter;
import com.nzt.converter.fbx.FbxConverterOptions;
import com.nzt.converter.utils.PropertiesFileReader;

import java.util.Properties;

public class Main {


    public static void main(String[] args) {
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
        String initPath = System.getProperty("user.dir");

        Properties properties = propertiesFileReader.read(initPath + "/fbx-to-gdx.properties");
        if(properties == null){
            System.out.println("fbx-to-gdx.properties is required");
        }
        FbxConverterOptions options = new FbxConverterOptions(properties);

        FbxConverter fbxConverter = new FbxConverter(options);
        fbxConverter.readDbAndConvertAll();
    }
}
