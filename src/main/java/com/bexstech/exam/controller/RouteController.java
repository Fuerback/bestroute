package com.bexstech.exam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bexstech.exam.services.RouteService;
import com.bexstech.exam.util.RoutesSingleton;

@RestController
@RequestMapping("route")
public class RouteController {

    private RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public String findRoute(@RequestParam String from, @RequestParam String to) {
        return routeService.findRoute( from + "-" + to, RoutesSingleton.getInstance().getRouteModels() ).toString();
    }

    @PutMapping
    public String insertRoute() {
        return "OK";
    }
}
