package sella.democsv.servcie.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.text.StringEscapeUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import sella.democsv.config.ApplicationProperties;
import sella.democsv.csvClassGenerated.CSVObject;
import sella.democsv.exception.EmptyFileException;
import sella.democsv.exception.IncorrectFileNameException;
import sella.democsv.model.NewCsvObject;
import sella.democsv.servcie.CsvService;
import sella.democsv.validation.ValidatorUtils;

@Service
public class CsvServiceImpl implements CsvService {

    private static ApplicationProperties applicationProperties;

    @Autowired
    public void setApplicationProperties(ApplicationProperties applicationProperties){
        CsvServiceImpl.applicationProperties = applicationProperties;
    }
    private static CellProcessor[] getProcessors() {
        final CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // id
                new NotNull(), // name
                new NotNull(), // email
                new NotNull(), // Country
                new NotNull(), // age
        };
        return processors;
    }

    public static String getValidIdentifier( String s) {
        s= s.replaceAll("\\*", "").trim();
        StringBuilder sb = new StringBuilder();
        if(!Character.isJavaIdentifierStart(s.charAt(0))) {
            sb.append("_");
        }
        int i=0;
        for (char c : s.toCharArray()) {
            if(i==0)
                c=Character.toLowerCase(c);
            i++;
            if(!Character.isJavaIdentifierPart(c)) {
                sb.append("_");
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public void setCsvData(List<CSVObject> csvObjects) throws IOException {
        ICsvBeanWriter csvWriter = new CsvBeanWriter(new FileWriter(applicationProperties.getFileName()), CsvPreference.STANDARD_PREFERENCE);
        final CellProcessor[] processors = getProcessors();
        Field[] fields = NewCsvObject.class.getDeclaredFields();
        String[] csvHeader = {
                fields[0].getName(),
                fields[1].getName(),
                fields[2].getName(),
                fields[3].getName(),
                fields[4].getName()};
        csvWriter.writeHeader(csvHeader);
        List<NewCsvObject> newCsvObjects = mapNewCsvObject(csvObjects);
        for (NewCsvObject newCsvObject : newCsvObjects) {
            csvWriter.write(newCsvObject, csvHeader, processors);
        }
        csvWriter.close();
    }

    @Override
    public void createPojoFromCsvHeader(File csvInputFile, String directoryOfjavaFile, String packageName,String packageRoute, String className) {
        try (BufferedReader stream = new BufferedReader(new FileReader(csvInputFile))) {
            String packagePath = packageName.replace(".", "/");
            String javaOutputDirPath = directoryOfjavaFile + "/" + packagePath + "/";
            System.out.println("creating directory ->" + javaOutputDirPath);
            File f = new File(javaOutputDirPath);
            if (f.mkdirs()) {
                System.out.println("directory :" + javaOutputDirPath + " created succesfully..");
            } else {
                System.out.println("directory :" + javaOutputDirPath + " already exist..");
            }
            String javaOutputFilePath = directoryOfjavaFile + "/" + packagePath + "/" + className + ".java";
            File javaOutPutFile = new File(javaOutputFilePath);
            javaOutPutFile.createNewFile();
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(javaOutputFilePath)));
            System.out.println("generating class..");
            out.println("package " + packageRoute + ";");
            out.println("public class " + className + " {");
            String line = null;
            String[] fields = null;
            int rowNum = 0;
            while ((line = stream.readLine()) != null) {
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                } else {
                    if (fields == null) {
                        fields = line.split(",");
                    }

                    rowNum++;
                    String[] values = line.split(",");
                    for (int i = 0; i < fields.length; i++) {
                        out.println("\t\t private String " + getValidIdentifier(fields[i]) + ";");
                    }

                    for (int i = 0; i < fields.length; i++) {

                        String ids = getValidIdentifier(values[i]);
                        String tempField = StringEscapeUtils.escapeJava(ids).substring(0, 1).toUpperCase() + StringEscapeUtils.escapeJava(ids).substring(1);

                        //getter method
                        out.println("");
                        out.println("\t\tpublic String  get" + tempField + "(){");
                        out.println("\t\t\treturn this." + StringEscapeUtils.escapeJava(ids) + ";");
                        out.println("\t\t}");
                        //setter method
                        out.println("\t\tpublic void  set" + tempField + "(String " + StringEscapeUtils.escapeJava(ids) + "){");
                        out.println("\t\t\t this." + StringEscapeUtils.escapeJava(ids) + " = " + StringEscapeUtils.escapeJava(ids) + ";");
                        out.println("\t\t}");
                    }

                    out.println("");
                }
                out.println("}");
                out.close();
            }
            System.out.println("no of lines fetch from csv :" + rowNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createNewCsv(String[] args) throws IOException {
        try {
            ValidatorUtils.validateArgs(args);
            File file = new File(args[0]);
            InputStream in = Files.newInputStream(file.toPath());
            Reader reader = new BufferedReader(new InputStreamReader(in));
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVObject.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<CSVObject> csvObjects = csvToBean.parse();
            setCsvData(csvObjects);
        } catch (IOException e) {
            throw e;
        } catch (IncorrectFileNameException | EmptyFileException e) {
            throw new RuntimeException(e);
        }
    }

    public List<NewCsvObject> mapNewCsvObject(List<CSVObject> csvObjects){
        List<NewCsvObject> newCsvObjects = new ArrayList<>();
        csvObjects.forEach(csvObject -> {
            NewCsvObject newCsvObject = new NewCsvObject();
            newCsvObject.setAge(String.valueOf(Integer.parseInt(csvObject.getAge() )* 100));
            newCsvObject.setName(csvObject.getName());
            newCsvObject.setId(csvObject.getId());
            newCsvObject.setEmails(csvObject.getEmail());
            newCsvObject.setCountry(csvObject.getCountry());
            newCsvObjects.add(newCsvObject);
        });
        return newCsvObjects;
    }
}