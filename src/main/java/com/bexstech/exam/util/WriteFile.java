package com.bexstech.exam.util;

import java.io.FileWriter;
import java.io.IOException;

import com.bexstech.exam.dto.RouteDTO;

public class WriteFile {

	public static void writeCSV(String filePath, RouteDTO routeDTO) throws IOException {
		FileWriter csvWriter = new FileWriter(filePath, true);

		csvWriter.append( routeDTO.getFrom());
		csvWriter.append(",");
		csvWriter.append( routeDTO.getTo());
		csvWriter.append(",");
		csvWriter.append( routeDTO.getPrice().toString());
		csvWriter.append("\n");

		csvWriter.flush();
		csvWriter.close();
	}
}
