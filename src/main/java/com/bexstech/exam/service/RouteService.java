package com.bexstech.exam.service;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.dto.RouteResponseDTO;
import com.bexstech.exam.engine.DijkstraAlgorithm;
import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.model.Graph;
import com.bexstech.exam.singleton.RouteSingleton;
import com.bexstech.exam.util.ReadFile;
import com.bexstech.exam.util.WriteFile;
import com.bexstech.exam.validation.ValidateInput;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private RouteDTO currentRoute;

    public RouteService() {
    }

    public void find(Graph graph, String route) {
        if (ValidateInput.isValid(route)) {
            currentRoute = RouteDTO.from(route);

            DijkstraAlgorithm engine = new DijkstraAlgorithm(graph);
            try {
                System.out.println(engine.calculateShortestPath(currentRoute.getFrom(), currentRoute.getTo()));
            } catch (BadRequestException e) {
                System.out.println(e.getMessage());
            }
        } else {
            throw new BadRequestException("invalid input");
        }
    }

    public RouteResponseDTO findRoute(String route, List<RouteDTO> routeDTOS) {
        if (ValidateInput.isValid(route)) {
            currentRoute = RouteDTO.from(route);

            validateRoute(routeDTOS, currentRoute);

            List<RouteDTO> staringPoints = findStaringPoints(currentRoute.getFrom(), routeDTOS);
            this.routeResultDTOS = new ArrayList();

            staringPoints.forEach(startRoute -> {
                routeDescription = this.currentRoute.getFrom();
                this.totalPrice = 0;
                buildRoute(startRoute, this.currentRoute.getTo(), routeDTOS);
            });

            return findCheapestRoute();
        } else {
            throw new BadRequestException("invalid input");
        }
    }

    public void insertRoute(RouteDTO routeDTO) throws IOException {
        if (ValidateInput.isValid(routeDTO)) {
            WriteFile.writeCSV(RouteSingleton.getInstance().getFilePath(), routeDTO);
            List<RouteDTO> routeDTOS = ReadFile.readCSV(RouteSingleton.getInstance().getFilePath());
            RouteSingleton.getInstance().updateRoutes(routeDTOS);
        } else {
            throw new BadRequestException("invalid input");
        }
    }

    private void validateRoute(List<RouteDTO> routeDTOS, RouteDTO routeDTO) {
        routeDTOS.stream()
                .filter(route -> route.getFrom().equalsIgnoreCase(routeDTO.getFrom()))
                .filter(route -> route.getTo().equalsIgnoreCase(routeDTO.getTo()))
                .findAny()
                .orElseThrow(() -> new BadRequestException("unknown route"));
    }

    private RouteResponseDTO findCheapestRoute() {
        return routeResultDTOS.stream()
                .min(Comparator.comparing(RouteResponseDTO::getTotalPrice))
                .orElseThrow(() -> new NoSuchElementException("no routes found"));
    }

    private List<RouteDTO> findStaringPoints(String startingPoint, List<RouteDTO> routeDTOS) {
        return routeDTOS.stream()
                .filter(routeModel -> routeModel.getFrom().equalsIgnoreCase(startingPoint))
                .collect(Collectors.toList());
    }

    private void buildRoute(RouteDTO routeDTO, String destination, List<RouteDTO> routeDTOS) {
        routeDescription += " - " + routeDTO.getTo();
        totalPrice += routeDTO.getPrice();
        String currentDestination = routeDTO.getTo();

        if (isCyclicalRoute(currentDestination)) {
            return;
        }

        if (!currentDestination.equalsIgnoreCase(destination)) {
            List<RouteDTO> staringPoints = findStaringPoints(routeDTO.getTo(), routeDTOS);
            staringPoints.forEach(currentRoute -> buildRoute(currentRoute, destination, routeDTOS));
        } else {
            routeResultDTOS.add(new RouteResponseDTO(routeDescription, totalPrice));
        }
    }

    private boolean isCyclicalRoute(String currentDestination) {
        return currentDestination.equalsIgnoreCase(this.currentRoute.getFrom());
    }
}
