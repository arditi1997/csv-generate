package sella.democsv.mapper;

import sella.democsv.csvClassGenerated.FirstCsvObject;
import sella.democsv.csvClassGenerated.SecondCsvObject;

import java.util.List;

public interface CsvMapper {
     List<SecondCsvObject> mapNewCsvObject(List<FirstCsvObject> csvObjects);
}
