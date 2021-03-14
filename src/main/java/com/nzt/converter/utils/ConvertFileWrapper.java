package com.nzt.converter.utils;

public class ConvertFileWrapper {

    public boolean toConvert;
    public String relativePath;
    public Long lastModifiedTime;

    public ConvertFileWrapper(boolean toConvert, String relativePath, Long lastModifiedTime) {
        this.toConvert = toConvert;
        this.relativePath = relativePath.replace("\\","/");
        this.lastModifiedTime = lastModifiedTime;
    }
}