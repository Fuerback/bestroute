package com.bexstech.exam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

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
        System.out.println( "file: " + file );

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("please enter the route: ");

            String route = scanner.next();
            if(EXIT.equalsIgnoreCase( route )) {break;}

            // find best route

            System.out.println(String.format("best route: %s", route));
        }
    }

}
