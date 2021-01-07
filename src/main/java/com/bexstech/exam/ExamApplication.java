package com.bexstech.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.service.RouteScannerService;
import com.bexstech.exam.singleton.RouteSingleton;
import com.bexstech.exam.util.ReadFile;

@SpringBootApplication
public class ExamApplication implements ApplicationRunner {

    @Value("${file}")
    private String filePath;

    private RouteScannerService routeScannerService;

    public ExamApplication(RouteScannerService routeScannerService) {
        this.routeScannerService = routeScannerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        List<RouteDTO> routeDTOS = ReadFile.readCSV( filePath );
        RouteSingleton.getInstance().updateRoutes( routeDTOS );
        RouteSingleton.getInstance().updateFilePath( filePath );

        routeScannerService.scan( routeDTOS );
    }

}
