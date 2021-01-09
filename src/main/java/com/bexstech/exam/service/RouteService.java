package com.bexstech.exam.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.engine.DijkstraAlgorithm;
import com.bexstech.exam.model.CheapestPath;
import com.bexstech.exam.model.Graph;
import com.bexstech.exam.singleton.RouteSingleton;
import com.bexstech.exam.util.ReadFile;
import com.bexstech.exam.util.WriteFile;
import com.bexstech.exam.validation.ValidateInput;

@Service
public class RouteService {
    public RouteService() {
    }

    public CheapestPath find(Graph graph, String route) {
        ValidateInput.isValid(route);

        RouteDTO currentRoute = RouteDTO.from(route);

        DijkstraAlgorithm engine = new DijkstraAlgorithm(graph);

        return engine.calculateShortestPath(currentRoute.getSource(), currentRoute.getDestination());
    }

    public void insert(RouteDTO routeDTO) throws IOException {
        ValidateInput.isValid(routeDTO);

        WriteFile.writeCSV(RouteSingleton.getInstance().getFilePath(), routeDTO);

        Graph graph = ReadFile.getGraphFromCSV(RouteSingleton.getInstance().getFilePath());

        RouteSingleton.getInstance().updateGraph(graph);
    }
}
