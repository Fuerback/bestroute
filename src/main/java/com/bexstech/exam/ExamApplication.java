package com.bexstech.exam;

import com.bexstech.exam.models.RouteModel;
import com.bexstech.exam.services.RouteService;
import com.bexstech.exam.util.ReadFile;
import com.bexstech.exam.util.ValidateInput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

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

            String route = scanner.next();

            if( ValidateInput.isValid(route) ) {
                String[] routeInput = route.split("-");
                RouteModel routeModel = new RouteModel( routeInput[0], routeInput[1], null );
                String bestRoute = routeService.findBestRoute(routeModel, routeModels);

                System.out.println(String.format("best route: %s", bestRoute));
            } else {
                System.out.println(String.format("the input is invalid"));
            }
        }
    }

}
