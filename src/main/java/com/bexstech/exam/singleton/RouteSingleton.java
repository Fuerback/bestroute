package com.bexstech.exam.singleton;

import java.util.ArrayList;
import java.util.List;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.model.Graph;

public class RouteSingleton {
	private static RouteSingleton instance;
	private List<RouteDTO> routeDTOS;
	private String filePath;
	private Graph graph;

	private RouteSingleton() {
		routeDTOS = new ArrayList<>();
		filePath = "";
	}

	public synchronized static RouteSingleton getInstance(){
		if(instance == null) {
			instance = new RouteSingleton();
		}
		return instance;
	}

	public synchronized void updateRoutes(List<RouteDTO> routeDTOS) {
		this.routeDTOS = routeDTOS;
	}

	public synchronized void updateFilePath(String filePath) {
		this.filePath = filePath;
	}

	public synchronized void updateGraph(Graph graph) {
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	public List<RouteDTO> getRouteModels() {
		return routeDTOS;
	}

	public String getFilePath() {
		return filePath;
	}
}
