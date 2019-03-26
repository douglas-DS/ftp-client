package br.com.douglas.ftpclient.util;

import br.com.douglas.ftpclient.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils implements Closeable {

    private static Logger logger = LoggerFactory.getLogger(CsvUtils.class);

    public String createFileWithCsvFormat(Student student) {
        String format = ".csv";
        String folder = "csv/";
        String path = folder + student.getName() + format;

        List<String> list = new ArrayList<>();
        Field[] fields = student.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String stringField;

            try {
                if (field.get(student) != null) stringField = field.get(student).toString();
                else stringField = "";

                list.add(stringField);

            } catch(IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String lines = this.writeLines(list);
        try (FileWriter file = new FileWriter(path)) {
            file.write(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Csv file created!");
        logger.info("Path: " + path);

        return path;
    }

    private String writeLines(List<String> values) {
        char separator = ';';
        boolean first = true;

        StringBuilder sb = new StringBuilder();

        for (String value : values) {
            if (!first) {
                sb.append(separator);
            }
            sb.append(value);

            first = false;
        }
        sb.append("\n");

        return sb.toString();
    }

    @Override
    public void close(){
    }
}
