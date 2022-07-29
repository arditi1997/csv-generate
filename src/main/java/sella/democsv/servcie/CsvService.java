package sella.democsv.servcie;

import sella.democsv.csvClassGenerated.CSVObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CsvService {

     void setCsvData(List<CSVObject> csvObjects) throws IOException;
     void createPojoFromCsvHeader(File csvInputFile, String directoryOfjavaFile, String packageName, String packageRoute, String className);
    void createNewCsv(String[] args) throws IOException;
}
