package sella.democsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sella.democsv.servcie.CsvService;
import sella.democsv.servcie.impl.CsvServiceImpl;

import java.io.IOException;

@SpringBootApplication
public class DemoCsvApplication {
    private static CsvService csvService;
    static {
        csvService = new CsvServiceImpl();
    }
    public static void main(String[] args) throws IOException {
        SpringApplication.run(DemoCsvApplication.class, args);
        args = new String[] {"/Users/arditmete/Desktop/temp.csv", "/Users/arditmete/Desktop/temp2.csv" };
        csvService.createPojoFromCsvHeader(args);
        csvService.createNewCsv(args);
    }
}
