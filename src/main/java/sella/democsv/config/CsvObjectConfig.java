package sella.democsv.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(ApplicationProperties.class)
@Configuration
public class CsvObjectConfig {
}

