package com.nzt.converter.utils.csv;

import com.nzt.converter.utils.ConvertFileWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompareFolderAndDB {

    public static List<ConvertFileWrapper> compare(final HashMap<String, String> mapFilesInFolder, final HashMap<String, String> csvValues) {
        List<ConvertFileWrapper> list = new ArrayList<>();
        for (Map.Entry<String, String> fileEntry : mapFilesInFolder.entrySet()) {
            String relativePathFile = fileEntry.getKey();
            String lastModifiedTime= fileEntry.getValue();
            boolean toConvert = true;
            if (csvValues.containsKey(relativePathFile) && lastModifiedTime.equals(csvValues.get(relativePathFile))) {
                toConvert = false;
            }
            System.out.println(relativePathFile + " last modif :" + lastModifiedTime + ", csvDate:" + csvValues.get(relativePathFile));

            ConvertFileWrapper wrapperConvertFile = new ConvertFileWrapper(toConvert, fileEntry.getKey(), fileEntry.getValue());
            list.add(wrapperConvertFile);
        }
        return list;
    }
}
