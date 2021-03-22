package com.nzt.converter.utils;

public class ConvertFileWrapper {

    public boolean toConvert;
    public String relativePath;
    public String lastModifiedTime;

    public ConvertFileWrapper(boolean toConvert, String relativePath, String lastModifiedTime) {
        this.toConvert = toConvert;
        this.relativePath = relativePath.replace("\\","/");
        this.lastModifiedTime = lastModifiedTime;
    }
}