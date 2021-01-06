package com.bexstech.exam;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bexstech.exam.exception.InvalidInputException;
import com.bexstech.exam.models.RouteModel;
import com.bexstech.exam.services.RouteService;
import com.bexstech.exam.util.ReadFile;

@SpringBootApplication
public class ExamApplication implements ApplicationRunner {

    @Value("${file}")
    private String filePath;

    private RouteService routeService;

    public ExamApplication(RouteService routeService) {
        this.routeService = routeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        List<RouteModel> routeModels = ReadFile.readCSV( filePath );

        Scanner scanner = new Scanner(System.in);

        while (!routeModels.isEmpty()) {
            System.out.print("please enter the route: ");

            String route = scanner.nextLine();

            try {
                String bestRoute = routeService.findBestRoute(route, routeModels);

                System.out.println(String.format("best route: %s", bestRoute));
            } catch (InvalidInputException e) {
                System.out.println(String.format("invalid route"));
            }
        }
    }

}
