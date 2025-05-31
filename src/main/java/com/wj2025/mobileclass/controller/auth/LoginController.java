package com.wj2025.mobileclass.controller.auth;

import com.wj2025.mobileclass.service.IService.user.IUserServiece;
import com.wj2025.mobileclass.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final IUserServiece userServiece;
    private final JwtUtils jwtUtils;
    public LoginController(IUserServiece userServiece, JwtUtils jwtUtils) {
        this.userServiece = userServiece;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequest loginRequest) {
        if(loginRequest.username.isEmpty() || loginRequest.password.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var user = userServiece.findByUsername(loginRequest.username);
        if(user.isPresent()) {
            var username = user.get().getUsername();
            var password = user.get().getPassword();
            if(password.equals(loginRequest.password)) {
                System.out.println("用户登陆成功："+username);
                String token = jwtUtils.generateToken(user.get().getUsername());
                return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
            }else{
                return new ResponseEntity<>("Username or Password Error", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Username or Password Error", HttpStatus.BAD_REQUEST);
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public void setUsername(String username) {
            this.username = username;
        }
    }
}
