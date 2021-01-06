package com.bexstech.exam.services;

import com.bexstech.exam.dto.RouteResponseDTO;
import com.bexstech.exam.exception.InvalidInputException;
import com.bexstech.exam.models.RouteModel;
import com.bexstech.exam.util.ValidateInput;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private String routeDescription;
    private Integer totalPrice;
    private List<RouteResponseDTO> routeResultDTOS;

    public RouteService() {
        this.routeDescription = "";
        this.totalPrice = 0;
        this.routeResultDTOS = new ArrayList();
    }

    public RouteResponseDTO findRoute(String route, List<RouteModel> routeModels) {
        if(!ValidateInput.isValid( route )) { throw new InvalidInputException(); }

        RouteModel routeModel = RouteModel.from( route );
        List<RouteModel> staringPoints = findStaringPoints(routeModel.getFrom(), routeModels);

        staringPoints.forEach(currentRoute -> {
            routeDescription = routeModel.getFrom();
            totalPrice = 0;
            buildRoute(currentRoute, routeModel.getTo(), routeModels);
        });

        return findCheaperRoute();
    }

    private RouteResponseDTO findCheaperRoute() {
        return routeResultDTOS.stream()
                .min( Comparator.comparing( RouteResponseDTO::getTotalPrice ) )
                .orElseThrow( NoSuchElementException::new );
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
