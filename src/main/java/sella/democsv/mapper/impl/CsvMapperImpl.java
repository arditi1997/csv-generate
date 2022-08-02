package sella.democsv.mapper.impl;

import org.springframework.stereotype.Service;
import sella.democsv.csvClassGenerated.FirstCsvObject;
import sella.democsv.csvClassGenerated.SecondCsvObject;
import sella.democsv.mapper.CsvMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvMapperImpl implements CsvMapper {

    @Override
    public List<SecondCsvObject> mapNewCsvObject(List<FirstCsvObject> firstCsvObjects) {
        List<SecondCsvObject> secondCsvObjects = new ArrayList<>();
        firstCsvObjects.forEach(csvObject -> {
            SecondCsvObject secondCsvObject = new SecondCsvObject();
            secondCsvObject.setAges(String.valueOf(Integer.parseInt(csvObject.getAge()) * 100));
            secondCsvObject.setNames(csvObject.getName());
            secondCsvObject.setIds(csvObject.getId());
            secondCsvObject.setEmails(csvObject.getEmail());
            secondCsvObject.setCountrys(csvObject.getCountrys());
            secondCsvObject.setPositions(csvObject.getPosition());
            secondCsvObjects.add(secondCsvObject);
        });
        return secondCsvObjects;
    }
}
