package com.nzt.converter.utils.csv;

import com.nzt.converter.utils.ConvertFileWrapper;
import com.nzt.converter.utils.Utils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvDb {

    private static String[] HEADERS = {"Path", "LastModificationDate"};
    private static char DELIMITER = ';';
    private String folderStartPath;
    private String csvFileName;
    public HashMap<String, String> csvValues;


    public CsvDb(String folderStartPath, String csvFileName) {
        this.folderStartPath = Utils.replacePath(folderStartPath);
        this.csvFileName = csvFileName;
        this.csvValues = new HashMap<>();
    }

    public HashMap<String, String> read() {
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
                String lastModifiedTime = record.get(HEADERS[1]);
                csvValues.put(path,lastModifiedTime);
            }
        } catch (IOException ioException) {
          return new HashMap<>();
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
            for (Map.Entry<String, String> stringLongEntry : csvValues.entrySet()) {
                printer.printRecord(stringLongEntry.getKey(), stringLongEntry.getValue());
            }
            printer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
