package com.nzt.converter.utils;

import com.nzt.converter.utils.csv.CompareFolderAndDB;
import com.nzt.converter.utils.csv.CsvDb;
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
        csvDb.csvValues.put("working/add1", "1213L");
        csvDb.csvValues.put("working/add2", "5555");

        csvDb.write();
        csvDb.csvValues = null;
        csvDb.read();
        Assertions.assertEquals(2, csvDb.csvValues.size());
        Assertions.assertNotNull(csvDb.csvValues.get("working/add1"));
        Assertions.assertEquals("5555", csvDb.csvValues.get("working/add2"));
    }

    @Test
    public void findFileAndCompareTest() {
        ClassLoader classLoader = getClass().getClassLoader();
        String startFolderPath = classLoader.getResource("csv/read-compare").getPath();
        CsvDb csvDb = new CsvDb(startFolderPath, "db-txt.csv");

        HashMap<String, String> csvValues = csvDb.read();
        HashMap<String, String> filesInFolder = FileFinder.findAllFiles(startFolderPath, ".txt");

        List<ConvertFileWrapper> convertFileWrappers = CompareFolderAndDB.compare(filesInFolder, csvValues);
        Assertions.assertEquals(4, convertFileWrappers.size());
        List<ConvertFileWrapper> fileToConvert = convertFileWrappers.stream().filter(f -> f.toConvert).collect(Collectors.toList());
        Assertions.assertEquals(4, fileToConvert.size());


        //MODIF
        HashMap<String, String> allTxtFiles = FileFinder.findAllFiles(startFolderPath, ".txt");
        csvDb.csvValues = allTxtFiles;
        csvDb.write();

        csvDb.csvValues.entrySet().stream().findFirst().get().setValue("wrongUselessDate");//modif timemillis

        convertFileWrappers  = CompareFolderAndDB.compare(filesInFolder, csvDb.csvValues);
        Assertions.assertEquals(4, convertFileWrappers.size());

        fileToConvert = convertFileWrappers.stream().filter(f -> f.toConvert).collect(Collectors.toList());
        Assertions.assertEquals(1, fileToConvert.size());
    }
}
