package com.nzt.converter.utils;

import com.nzt.converter.utils.ConvertFileWrapper;
import com.nzt.converter.utils.FileFinder;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDb {

    private static String[] HEADERS = {"Path", "Timemillis"};
    private static char DELIMITER = '#';
    private String folderStartPath;
    private String csvFileName;
    public HashMap<String, Long> csvValues;


    public CsvDb(String folderStartPath, String csvFileName) {
        this.folderStartPath = Utils.replacePath(folderStartPath);
        this.csvFileName = csvFileName;
        this.csvValues = new HashMap<>();
    }

    public List<ConvertFileWrapper> compareWithExisting(String extensionFiles) {
        Path relativePath = Paths.get(folderStartPath);
        List<ConvertFileWrapper> list = new ArrayList<>();
        HashMap<String, Long> mapFilesInFolder = FileFinder.findAllFiles(folderStartPath, extensionFiles);

        for (Map.Entry<String, Long> fileEntry : mapFilesInFolder.entrySet()) {
            File fbxFile = new File(relativePath + "/" + fileEntry.getKey());
            String relativePathFile = fileEntry.getKey();
            Long lastModifiedMillis = fileEntry.getValue();
            if (fbxFile.exists()) {
                boolean toConvert = true;
                if (csvValues.containsKey(relativePathFile) && lastModifiedMillis.equals(csvValues.get(relativePathFile))) {
                    toConvert = false;
                }
                ConvertFileWrapper wrapperConvertFile = new ConvertFileWrapper(toConvert, fileEntry.getKey(), fileEntry.getValue());
                list.add(wrapperConvertFile);
            }
        }
        return list;
    }

    public HashMap<String, Long> read() {
        Reader in = null;
        csvValues = new HashMap<>();
        try {
            in = new FileReader(folderStartPath + "/" + csvFileName);

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withDelimiter(DELIMITER)
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                String path = record.get(HEADERS[0]);
                String millis = record.get(HEADERS[1]);
                csvValues.put(path, Long.valueOf(millis));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return this.csvValues;
    }

    public void write(List<ConvertFileWrapper> list) {
        this.csvValues = new HashMap<>();
        list.stream().forEach(f -> this.csvValues.put(f.relativePath, f.lastModifiedTime));
        write();
    }


    public void write() {
        try {
            FileWriter out = null;
            out = new FileWriter(folderStartPath + "/" + csvFileName);
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT
                    .withDelimiter(DELIMITER)
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader());
            printer.printRecord(HEADERS[0], HEADERS[1]);
            for (Map.Entry<String, Long> stringLongEntry : csvValues.entrySet()) {
                printer.printRecord(stringLongEntry.getKey(), stringLongEntry.getValue());
            }
            printer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
