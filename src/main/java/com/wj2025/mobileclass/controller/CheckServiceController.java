package com.wj2025.mobileclass.controller;

import com.wj2025.mobileclass.model.ServiceStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/api")
public class CheckServiceController {
    @GetMapping("/CheckService")
    public ServiceStatus CheckService() {
        return new ServiceStatus("OK");
    }

    @GetMapping("/CheckAuth")
    public ResponseEntity<?>CheckAuth(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var resp = new HashMap<String, String>();
        resp.put("username", username);
        return ResponseEntity.ok(resp);
    }
}
