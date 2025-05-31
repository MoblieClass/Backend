package com.wj2025.mobileclass.controller;

import com.wj2025.mobileclass.model.ServiceStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/api")
@Tag(
        name = "系统检查",
        description = "检查系统是否正常运行"
)
public class CheckServiceController {
    @GetMapping("/CheckService")
    @Operation(summary = "系统检查接口")
    public ServiceStatus CheckService() {
        return new ServiceStatus("OK");
    }

    @GetMapping("/CheckAuth")
    @Operation(summary = "系统认证接口，返回认证用户名")
    public ResponseEntity<?>CheckAuth(){
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        var resp = new HashMap<String, String>();
        resp.put("username", username);
        return ResponseEntity.ok(resp);
    }
}
