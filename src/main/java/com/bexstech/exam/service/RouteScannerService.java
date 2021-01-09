package com.bexstech.exam.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.exception.NotFoundException;
import com.bexstech.exam.singleton.RouteSingleton;

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
            } catch (BadRequestException | IllegalArgumentException | NotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
