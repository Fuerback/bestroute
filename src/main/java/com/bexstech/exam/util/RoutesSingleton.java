package com.bexstech.exam.util;

import java.util.ArrayList;
import java.util.List;

import com.bexstech.exam.models.RouteModel;

public class RoutesSingleton {
	private static RoutesSingleton instance;
	private List<RouteModel> routeModels;
	private String filePath;

	private RoutesSingleton() {
		routeModels = new ArrayList<>();
		filePath = "";
	}

	public synchronized static RoutesSingleton getInstance(){
		if(instance == null) {
			instance = new RoutesSingleton();
		}
		return instance;
	}

	public synchronized void updateRoutes(List<RouteModel> routeModels) {
		this.routeModels = routeModels;
	}

	public synchronized void updateFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<RouteModel> getRouteModels() {
		return routeModels;
	}

	public String getFilePath() {
		return filePath;
	}
}
