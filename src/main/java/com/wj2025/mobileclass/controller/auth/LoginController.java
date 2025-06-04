package com.wj2025.mobileclass.controller.auth;

import com.wj2025.mobileclass.model.user.UserModel;
import com.wj2025.mobileclass.service.IService.user.IUserServiece;
import com.wj2025.mobileclass.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "认证管理",
        description = "登陆认证相关接口"
)
public class LoginController {

    private final IUserServiece userServiece;
    private final JwtUtils jwtUtils;
    public LoginController(IUserServiece userServiece, JwtUtils jwtUtils) {
        this.userServiece = userServiece;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    @Operation(summary = "登陆接口")
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

    @PostMapping("/register")
    @Operation(summary = "注册用户")
    public ResponseEntity<?>registerUser(@RequestBody RegisterRequest request) {
        if(request.username==null || request.password==null || request.email==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        var usernameUser = userServiece.findByUsername(request.username);
        if(usernameUser.isPresent()) {
            return new ResponseEntity<>("Username already exist", HttpStatus.BAD_REQUEST);
        }
        var emailUser = userServiece.findByEmail(request.email);
        if(emailUser.isPresent()) {
            return new ResponseEntity<>("Email already exist", HttpStatus.BAD_REQUEST);
        }
        var newUser = new UserModel();
        newUser.setId(null);
        newUser.setUsername(request.username);
        newUser.setPassword(request.password);
        newUser.setEmail(request.email);
        return new ResponseEntity<>(userServiece.save(newUser), HttpStatus.CREATED);
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

    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
