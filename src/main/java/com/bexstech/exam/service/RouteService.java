package com.bexstech.exam.service;

import com.bexstech.exam.dto.RouteResponseDTO;
import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.dto.RouteDTO;
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
    private final List<RouteResponseDTO> routeResultDTOS;

    public RouteService() {
        this.routeDescription = "";
        this.totalPrice = 0;
        this.routeResultDTOS = new ArrayList();
    }

    public RouteResponseDTO findRoute(String route, List<RouteDTO> routeDTOS) {
        if(!ValidateInput.isValid( route )) { throw new BadRequestException("invalid route"); }

        RouteDTO routeDTO = RouteDTO.from( route );
        List<RouteDTO> staringPoints = findStaringPoints( routeDTO.getFrom(), routeDTOS );

        staringPoints.forEach(currentRoute -> {
            routeDescription = routeDTO.getFrom();
            totalPrice = 0;
            buildRoute(currentRoute, routeDTO.getTo(), routeDTOS );
        });

        return findCheaperRoute();
    }

    private RouteResponseDTO findCheaperRoute() {
        return routeResultDTOS.stream()
                .min( Comparator.comparing( RouteResponseDTO::getTotalPrice ) )
                .orElseThrow( NoSuchElementException::new );
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
        if (!currentDestination.equalsIgnoreCase(destination)) {
            List<RouteDTO> staringPoints = findStaringPoints( routeDTO.getTo(), routeDTOS );
            staringPoints.forEach(currentRoute -> buildRoute(currentRoute, destination, routeDTOS ));
        } else {
            routeResultDTOS.add(new RouteResponseDTO(routeDescription, totalPrice));
        }
    }
}
