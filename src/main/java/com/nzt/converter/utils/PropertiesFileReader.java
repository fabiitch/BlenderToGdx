package com.nzt.converter.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

public class PropertiesFileReader {

    public PropertiesFileReader() {

    }

    public Properties read(String path) {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(path)) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return null;
            }
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }
}
