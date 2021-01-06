package com.bexstech.exam.services;

import com.bexstech.exam.dto.RouteResponseDTO;
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
    private List<RouteResponseDTO> routeResultDTOS = new ArrayList();

    public String findBestRoute(RouteModel routeModel, List<RouteModel> routeModels) {
        // validar o input aqui dentro pra facilitar o teste
        // remover a validação do application
        List<RouteModel> staringPoints = findStaringPoints(routeModel.getFrom(), routeModels);

        staringPoints.forEach(currentRoute -> {
            routeDescription = routeModel.getFrom();
            totalPrice = 0;
            buildRoute(currentRoute, routeModel.getTo(), routeModels);
        });

        RouteResponseDTO routeResultDTO = routeResultDTOS.stream()
                .min(Comparator.comparing(RouteResponseDTO::getTotalPrice))
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
            routeResultDTOS.add(new RouteResponseDTO(routeDescription, totalPrice));
        }
    }
}
