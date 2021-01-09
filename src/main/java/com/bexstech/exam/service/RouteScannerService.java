package com.bexstech.exam.service;

import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.model.Graph;
import com.bexstech.exam.singleton.RouteSingleton;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class RouteScannerService {

    private static String EXIT = "exit";

    public RouteScannerService() {
    }

    public void scan() {
        Scanner scanner = new Scanner(System.in);
        RouteService routeService;
        String route;

        while (true) {
            System.out.print("please enter the route: ");

            route = scanner.nextLine();

            if (EXIT.equalsIgnoreCase(route)) {
                System.out.println(route);
                break;
            }

            try {
                routeService = new RouteService();
                System.out.println("best route: " + routeService.find(RouteSingleton.getInstance().getGraph(), route).toString());
            } catch (BadRequestException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
