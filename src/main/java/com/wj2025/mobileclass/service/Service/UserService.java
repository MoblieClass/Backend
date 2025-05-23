package com.wj2025.mobileclass.service.Service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserService userService;
    public UserService(UserService userService) {
        this.userService = userService;
    }
}
