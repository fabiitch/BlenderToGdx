package com.nzt.converter.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CsvDbTest {

    @Test
    public void writeAndReadTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        String startFolderPath = classLoader.getResource("csv").getPath();

        CsvDb csvDb = new CsvDb(startFolderPath, "db-write.csv");
        csvDb.csvValues.put("working/add1", 1213L);
        csvDb.csvValues.put("working/add2", 12132012L);

        csvDb.write();
        csvDb.csvValues = null;
        csvDb.read();
        Assertions.assertEquals(2, csvDb.csvValues.size());
        Assertions.assertNotNull(csvDb.csvValues.get("working/add1"));
        Assertions.assertEquals(csvDb.csvValues.get("working/add2"), 12132012L);
    }

    @Test
    public void findFileAndCompareTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        String startFolderPath = classLoader.getResource("csv/read-compare").getPath();
        CsvDb csvDb = new CsvDb(startFolderPath, "db-txt.csv");

        List<ConvertFileWrapper> convertFileWrappers = csvDb.compareWithExisting(".txt");
        Assertions.assertEquals(4, convertFileWrappers.size());
        List<ConvertFileWrapper> fileToConvert = convertFileWrappers.stream().filter(f -> f.toConvert).collect(Collectors.toList());
        Assertions.assertEquals(4, fileToConvert.size());


        //MODIF
        HashMap<String, Long> allTxtFiles = FileFinder.findAllFiles(startFolderPath, ".txt");
        csvDb.csvValues = allTxtFiles;
        csvDb.write();

        csvDb.csvValues.entrySet().stream().findFirst().get().setValue(3232L);//modif timemillis

        convertFileWrappers = csvDb.compareWithExisting(".txt");
        Assertions.assertEquals(4, convertFileWrappers.size());

        fileToConvert = convertFileWrappers.stream().filter(f -> f.toConvert).collect(Collectors.toList());
        Assertions.assertEquals(1, fileToConvert.size());
    }
}
