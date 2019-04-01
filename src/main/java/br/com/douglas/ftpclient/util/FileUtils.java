package br.com.douglas.ftpclient.util;

import br.com.douglas.ftpclient.model.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    private String filePath;

    public File createFile(Student student) {
        String path = new CsvUtils().createFileWithCsvFormat(student);
        filePath = path;
        return new File(path);
    }

    public void eraseFile() {
        try {
            Path p = Paths.get(filePath);
            Files.deleteIfExists(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
