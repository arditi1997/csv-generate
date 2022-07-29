package sella.democsv.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApplicationProperties {
    @Value("${csv.file-name}")
    private String fileName;
    @Value("${csv.object-created.directory-of-java-fIle}")
    private String directoryOfJavaFIle;
    @Value("${csv.object-created.package-name}")
    private String packageName;
    @Value("${csv.object-created.package-route}")
    private String packageRoute;
    @Value("${csv.object-created.name}")
    private String name;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDirectoryOfJavaFIle() {
        return directoryOfJavaFIle;
    }

    public void setDirectoryOfJavaFIle(String directoryOfJavaFIle) {
        this.directoryOfJavaFIle = directoryOfJavaFIle;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageRoute() {
        return packageRoute;
    }

    public void setPackageRoute(String packageRoute) {
        this.packageRoute = packageRoute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

