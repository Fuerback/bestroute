package com.bexstech.exam;

import com.bexstech.exam.models.RouteModel;
import com.bexstech.exam.services.RouteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ExamApplication implements ApplicationRunner {

    @Value("${file}")
    private String filePath;

    private final String EXIT = "exit";

    private RouteService routeService;

    public ExamApplication(RouteService routeService) {
        this.routeService = routeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<RouteModel> routeModels = new ArrayList();

        System.out.println("file: " + filePath);
        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            routeModels.add(new RouteModel(data[0], data[1], Integer.parseInt(data[2])));
        }
        csvReader.close();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("please enter the route: ");

            String route = scanner.next();
            if (EXIT.equalsIgnoreCase(route)) {
                break;
            }

            String[] routeList = route.split("-");
            String bestRoute = routeService.findBestRoute(routeList[0], routeList[1], routeModels);

            System.out.println(String.format("best route: %s", bestRoute));
        }
    }

}
