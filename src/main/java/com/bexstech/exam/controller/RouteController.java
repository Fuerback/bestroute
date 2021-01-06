package com.bexstech.exam.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.service.RouteService;
import com.bexstech.exam.util.ReadFile;
import com.bexstech.exam.util.RoutesSingleton;
import com.bexstech.exam.util.ValidateInput;
import com.bexstech.exam.util.WriteFile;

@RestController
@RequestMapping("route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public String findRoute(@RequestParam String from, @RequestParam String to) {
        return routeService.findRoute( from + "-" + to, RoutesSingleton.getInstance().getRouteModels() ).toString();
    }

    @PutMapping
    public void insertRoute(@RequestBody RouteDTO routeDTO) throws IOException {
        if( ValidateInput.isValid( routeDTO ) ) {
            WriteFile.writeCSV( RoutesSingleton.getInstance().getFilePath(), routeDTO );
            List<RouteDTO> routeDTOS = ReadFile.readCSV( RoutesSingleton.getInstance().getFilePath() );
            RoutesSingleton.getInstance().updateRoutes( routeDTOS );
        } else {
            throw new BadRequestException("invalid route");
        }
    }
}
