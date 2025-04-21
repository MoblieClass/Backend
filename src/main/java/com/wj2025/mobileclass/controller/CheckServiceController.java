package com.wj2025.mobileclass.controller;

import com.wj2025.mobileclass.model.ServiceStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CheckServiceController {
    @GetMapping("/CheckService")
    public ServiceStatus CheckService() {
        return new ServiceStatus("OK");
    }
}
