package com.bexstech.exam.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bexstech.exam.models.RouteModel;
import com.bexstech.exam.services.RouteService;
import com.bexstech.exam.util.ReadFile;
import com.bexstech.exam.util.RoutesSingleton;
import com.bexstech.exam.util.WriteFile;

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
    public void insertRoute(@RequestBody RouteModel routeModel) throws IOException {
        WriteFile.writeCSV( RoutesSingleton.getInstance().getFilePath(), routeModel);
        List<RouteModel> routeModels = ReadFile.readCSV( RoutesSingleton.getInstance().getFilePath() );
        RoutesSingleton.getInstance().updateRoutes( routeModels );
    }
}
