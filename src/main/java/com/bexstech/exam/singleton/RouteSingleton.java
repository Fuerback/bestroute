package com.bexstech.exam.singleton;

import java.util.ArrayList;
import java.util.List;

import com.bexstech.exam.dto.RouteDTO;

public class RouteSingleton {
	private static RouteSingleton instance;
	private List<RouteDTO> routeDTOS;
	private String filePath;

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

	public List<RouteDTO> getRouteModels() {
		return routeDTOS;
	}

	public String getFilePath() {
		return filePath;
	}
}
