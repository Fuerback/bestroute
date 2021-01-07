package com.bexstech.exam;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.dto.RouteResponseDTO;
import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.service.RouteService;
import com.bexstech.exam.util.ReadFile;
import com.bexstech.exam.util.RoutesSingleton;

@SpringBootApplication
public class ExamApplication implements ApplicationRunner {

    @Value("${file}")
    private String filePath;

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        List<RouteDTO> routeDTOS = ReadFile.readCSV( filePath );
        RoutesSingleton.getInstance().updateRoutes( routeDTOS );
        RoutesSingleton.getInstance().updateFilePath( filePath );

        Scanner scanner = new Scanner(System.in);

        while (!routeDTOS.isEmpty()) {
            System.out.print("please enter the route: ");

            String route = scanner.nextLine();

            try {
                RouteService routeService = new RouteService();
                RouteResponseDTO routeResponseDTO = routeService.findRoute( route, RoutesSingleton.getInstance().getRouteModels() );

                System.out.println(String.format("best route: %s", routeResponseDTO.toString()));
            } catch (BadRequestException e) {
                System.out.println(String.format(e.getMessage()));
            } catch (NoSuchElementException e) {
                System.out.println(String.format("no routes found"));
            }
        }
    }

}
