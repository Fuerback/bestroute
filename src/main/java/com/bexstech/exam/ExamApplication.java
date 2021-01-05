package com.bexstech.exam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ExamApplication implements ApplicationRunner {

    @Value("${name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }

    @Override
    public void run( ApplicationArguments args ) throws Exception
    {
        System.out.println( "Name: " + name );

        // create a scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);

        //  prompt for the user's name
        System.out.print("Enter your name: ");

        // get their input as a String
        String username = scanner.next();

        // prompt for their age
        System.out.print("Enter your age: ");

        // get the age as an int
        int age = scanner.nextInt();

        System.out.println(String.format("%s, your age is %d", username, age));
    }

}
