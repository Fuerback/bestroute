package com.bexstech.exam.util;

import java.io.FileWriter;
import java.io.IOException;

import com.bexstech.exam.models.RouteModel;

public class WriteFile {

	public static void writeCSV(String filePath, RouteModel routeModel) throws IOException {
		FileWriter csvWriter = new FileWriter(filePath, true);

		csvWriter.append(routeModel.getFrom());
		csvWriter.append(",");
		csvWriter.append(routeModel.getTo());
		csvWriter.append(",");
		csvWriter.append(routeModel.getPrice().toString());
		csvWriter.append("\n");

		csvWriter.flush();
		csvWriter.close();
	}
}
