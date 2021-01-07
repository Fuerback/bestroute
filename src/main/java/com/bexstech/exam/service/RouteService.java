package com.bexstech.exam.service;

import com.bexstech.exam.dto.RouteResponseDTO;
import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.util.ReadFile;
import com.bexstech.exam.util.RoutesSingleton;
import com.bexstech.exam.util.ValidateInput;
import com.bexstech.exam.util.WriteFile;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {

    private String routeDescription;
    private Integer totalPrice;
    private final List<RouteResponseDTO> routeResultDTOS;
    private RouteDTO currentRoute;

    public RouteService() {
        this.routeDescription = "";
        this.totalPrice = 0;
        this.routeResultDTOS = new ArrayList();
    }

    public RouteResponseDTO findRoute(String route, List<RouteDTO> routeDTOS) {
        if(!ValidateInput.isValid( route )) { throw new BadRequestException("invalid input"); }

        currentRoute = RouteDTO.from( route );

        validateRoute( routeDTOS, currentRoute );

        List<RouteDTO> staringPoints = findStaringPoints( currentRoute.getFrom(), routeDTOS );

        staringPoints.forEach(currentRoute -> {
            routeDescription = this.currentRoute.getFrom();
            totalPrice = 0;
            buildRoute(currentRoute, this.currentRoute.getTo(), routeDTOS );
        });

        return findCheaperRoute();
    }

    private void validateRoute(List<RouteDTO> routeDTOS, RouteDTO routeDTO) {
        routeDTOS.stream()
                .filter( route -> route.getFrom().equalsIgnoreCase( routeDTO.getFrom() ) )
                .findAny()
                .orElseThrow( () -> new BadRequestException( "invalid start point" ) );
        routeDTOS.stream()
                .filter( route -> route.getTo().equalsIgnoreCase( routeDTO.getTo() ) )
                .findAny()
                .orElseThrow( () -> new BadRequestException( "invalid destination route" ) );
    }

    public void insertRoute(RouteDTO routeDTO) throws IOException {
        if( ValidateInput.isValid( routeDTO ) ) {
            WriteFile.writeCSV( RoutesSingleton.getInstance().getFilePath(), routeDTO );
            List<RouteDTO> routeDTOS = ReadFile.readCSV( RoutesSingleton.getInstance().getFilePath() );
            RoutesSingleton.getInstance().updateRoutes( routeDTOS );
        } else {
            throw new BadRequestException("invalid input");
        }
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
        if(currentDestination.equalsIgnoreCase( this.currentRoute.getFrom() )){return;}
        if (!currentDestination.equalsIgnoreCase(destination)) {
            List<RouteDTO> staringPoints = findStaringPoints( routeDTO.getTo(), routeDTOS );
            staringPoints.forEach(currentRoute -> buildRoute(currentRoute, destination, routeDTOS ));
        } else {
            routeResultDTOS.add(new RouteResponseDTO(routeDescription, totalPrice));
        }
    }
}
