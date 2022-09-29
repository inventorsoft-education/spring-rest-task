package main;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class ReadFile {
    public LinkedList<Email> readBackup(String path) {
        LinkedList<Email> emails = new LinkedList<>();
        try (FileReader fileReader = new FileReader(path)) {
            CSVReader csvReader = new CSVReaderBuilder(fileReader).build();
            List<String[]> strings = csvReader.readAll();
            for (String[] arr : strings) {
                Email email = new Email(arr[1], arr[2], arr[3], arr[4], LocalDateTime.parse(arr[5]));
                email.setId(Long.parseLong(arr[0]));
                emails.add(email);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return emails;
    }

    public void saveBackup(String path, List<Email> emails) {
        try (Writer writer = new FileWriter(path)) {
            StatefulBeanToCsv statefulBeanToCsv = new StatefulBeanToCsvBuilder(writer).build();
            statefulBeanToCsv.write(emails);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }
}
