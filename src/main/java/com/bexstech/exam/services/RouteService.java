package com.bexstech.exam.services;

import com.bexstech.exam.dto.RouteResultDTO;
import com.bexstech.exam.models.RouteModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private String routeDescription = "";
    private Integer totalPrice = 0;
    private List<RouteResultDTO> routeResultDTOS = new ArrayList();

    public String findBestRoute(String startingPoint, String destination, List<RouteModel> routeModels) {
        List<RouteModel> staringPoints = findStaringPoints(startingPoint, routeModels);

        staringPoints.forEach(currentRoute -> {
            routeDescription = startingPoint;
            totalPrice = 0;
            buildRoute(currentRoute, destination, routeModels);
        });

        RouteResultDTO routeResultDTO = routeResultDTOS.stream()
                .min(Comparator.comparing(RouteResultDTO::getTotalPrice))
                .orElseThrow(NoSuchElementException::new);

        return routeResultDTO.toString();
    }

    private List<RouteModel> findStaringPoints(String startingPoint, List<RouteModel> routeModels) {
        return routeModels.stream()
                .filter(routeModel -> routeModel.getFrom().equalsIgnoreCase(startingPoint))
                .collect(Collectors.toList());
    }

    private void buildRoute(RouteModel routeModel, String destination, List<RouteModel> routeModels) {
        routeDescription += " - " + routeModel.getTo();
        totalPrice += routeModel.getPrice();
        String currentDestination = routeModel.getTo();
        if (!currentDestination.equalsIgnoreCase(destination)) {
            List<RouteModel> staringPoints = findStaringPoints(routeModel.getTo(), routeModels);
            staringPoints.forEach(currentRoute -> buildRoute(currentRoute, destination, routeModels));
        } else {
            routeResultDTOS.add(new RouteResultDTO(routeDescription, totalPrice));
        }
    }
}
