package com.bexstech.exam.util;

import java.util.ArrayList;
import java.util.List;

import com.bexstech.exam.dto.RouteDTO;

public class RoutesSingleton {
	private static RoutesSingleton instance;
	private List<RouteDTO> routeDTOS;
	private String filePath;

	private RoutesSingleton() {
		routeDTOS = new ArrayList<>();
		filePath = "";
	}

	public synchronized static RoutesSingleton getInstance(){
		if(instance == null) {
			instance = new RoutesSingleton();
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
