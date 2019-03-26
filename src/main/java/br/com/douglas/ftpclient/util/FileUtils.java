package br.com.douglas.ftpclient.util;

import br.com.douglas.ftpclient.model.Student;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Getter @Setter
public class FileUtils {

    private String filePath;

    public File createFile(Student student) {
        CsvUtils utils = new CsvUtils();
        String path = utils.createFileWithCsvFormat(student);
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
