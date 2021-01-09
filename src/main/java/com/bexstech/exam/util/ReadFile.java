package com.bexstech.exam.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.bexstech.exam.dto.RouteDTO;
import com.bexstech.exam.model.Graph;

public class ReadFile {

	public static List<RouteDTO> readCSV(String filePath) {
		List<RouteDTO> routeDTOS = new ArrayList();

		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
			String row;
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",");
				routeDTOS.add(new RouteDTO(data[0], data[1], Integer.parseInt(data[2])));
			}
			csvReader.close();
		} catch (Exception e) {
			System.out.println("the file does not exists or is invalid.");
		}

		return routeDTOS;
	}

	public static Graph getGraphFromCSV(String filePath) {
		Graph graph = new Graph();

		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
			String row;
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",");
				graph.addConnected(data[0], data[1], Integer.parseInt(data[2]));
			}
			csvReader.close();
		} catch (Exception e) {
			System.out.println("the file does not exists or is invalid.");
		}

		return graph;
	}
}
