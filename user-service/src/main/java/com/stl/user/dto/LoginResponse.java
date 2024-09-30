package com.stl.user.dto;

import com.stl.user.entity.User;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
    private User user;
}