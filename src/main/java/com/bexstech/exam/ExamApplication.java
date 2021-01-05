package com.bexstech.exam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bexstech.exam.entity.Route;

@SpringBootApplication
public class ExamApplication implements ApplicationRunner {

    @Value("${file}")
    private String file;

    private final String EXIT = "exit";

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

    @Override
    public void run( ApplicationArguments args ) throws Exception
    {
        List<Route> routes = new ArrayList();

        System.out.println( "file: " + file );
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row;
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            routes.add( new Route( data[0], data[1], Integer.parseInt( data[2] ) ) );
        }
        csvReader.close();

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("please enter the route: ");

            String route = scanner.next();
            if(EXIT.equalsIgnoreCase( route )) {break;}

            // find best route

            System.out.println(String.format("best route: %s", routes.get( 0 ).toString()));
        }
    }

}
