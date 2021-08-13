package com.ds.ftpclient.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Create a csv file based on object fields and values.
 */

@Slf4j
@Component
public class CsvFileGenerator {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final String EXTENSION = ".csv";
    private static final String DELIMITER = ";";
    
    private String resourceName;

    public void eraseFile() {
        try {
            var path = Paths.get(resourceName);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File createFile(@NonNull Map<String, Object> data) {
        this.resourceName = UUID.randomUUID() + EXTENSION;
        File file;
        try (var bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.resourceName), CHARSET))) {
            var headers = String.join(DELIMITER, data.keySet());
            bw.write(headers);
            bw.newLine();
            var values = String.join(DELIMITER, data.values().stream().map(Object::toString).toList());
            bw.write(values);
            bw.flush();
            bw.close();
            file = new File(this.resourceName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed creating CSV file.");
        }
        return file;
    }

}
