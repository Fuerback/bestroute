package com.bexstech.exam.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.service.RouteService;
import com.bexstech.exam.singleton.RouteSingleton;

@RestController
@RequestMapping("route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public ResponseEntity findRoute(@RequestParam String from, @RequestParam String to) {
        return ResponseEntity.ok( routeService.find( RouteSingleton.getInstance().getGraph(), from + "-" + to ).toString() );
    }

    @PutMapping
    public ResponseEntity insertRoute(@RequestBody RouteDTO routeDTO) throws IOException {
        routeService.insert( routeDTO );
        return ResponseEntity.noContent().build();
    }
}
