package siwei.ahon.qualitySafetyInspection;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "siwei.ahon.qualitySafetyInspection.mapper")
public class QualitySafetyInspectionApplication {
    public static void main(String[] args) {
          SpringApplication.run(QualitySafetyInspectionApplication.class, args);
        System.out.println("http://localhost:8080");
     }
}
