package com.nzt.converter.utils;

public class WrapperConvertFile {

    public boolean toConvert;
    public String relativePath;
    public Long lastModifiedTime;

    public WrapperConvertFile(boolean toConvert, String relativePath, Long lastModifiedTime) {
        this.toConvert = toConvert;
        this.relativePath = relativePath.replace("\\","/");
        this.lastModifiedTime = lastModifiedTime;
    }
}