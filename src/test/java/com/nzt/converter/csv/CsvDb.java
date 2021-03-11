package com.nzt.converter.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class CsvDb {

    private static String[] HEADERS = {"Name", "Path", "Timemillis"};
    private static String CSV_FILE_NAME = "fbx-convert-db.csv";
    private static char DELIMITER = '#';

    Map<String, Long> values;

    public CsvDb(String fbxFolderPath) {
        Reader in = null;
        values = new HashMap<>();
        try {
            in = new FileReader(fbxFolderPath + "/" + CSV_FILE_NAME);

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withDelimiter(DELIMITER)
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(in);
            for (CSVRecord record : records) {
                String name = record.get(HEADERS[0]);
                String path = record.get(HEADERS[1]);
                String millis = record.get(HEADERS[2]);
                values.put(name + "/" + path, Long.valueOf(millis));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
