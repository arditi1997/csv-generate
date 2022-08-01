package sella.democsv.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("csv-object")
@Data
public class ApplicationProperties {
    private String fileName;
    private String directoryOfJavaFile;
    private String packageName;
    private String packageRoute;
    private String firstCsvName;
    private String secondCsvName;
}


