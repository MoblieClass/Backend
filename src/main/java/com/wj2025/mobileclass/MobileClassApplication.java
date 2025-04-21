package com.wj2025.mobileclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MobileClassApplication {

    public static void main(String[] args) {
        System.out.println("Server is running at http://localhost:8080/");
        SpringApplication.run(MobileClassApplication.class, args);
    }

}
