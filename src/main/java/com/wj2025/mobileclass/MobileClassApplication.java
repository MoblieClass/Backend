package com.wj2025.mobileclass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MobileClassApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MobileClassApplication.class, args);
        Environment env = context.getEnvironment();
        int port = env.getProperty("server.port", Integer.class, 8080); // 默认 8080
        System.out.println("Server is running at http://localhost:" + port);
        System.out.println("Swagger: http://localhost:"+port+"/swagger-ui/index.html");
    }

}
