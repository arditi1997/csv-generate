package sella.democsv.servcie;

import sella.democsv.csvClassGenerated.FirstCsvObject;

import java.io.IOException;
import java.util.List;

public interface CsvService {

     void setCsvData(List<FirstCsvObject> csvObjects) throws IOException;
     void createPojoFromCsvHeader(String[] args);
    void createNewCsv(String[] args) throws IOException;
}
