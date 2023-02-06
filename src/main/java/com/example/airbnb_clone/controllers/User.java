package com.example.airbnb_clone.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Auth", description="권한, 인증")
@RestController
@RequestMapping("/user")
public class User {

    @Operation(summary="로그인", description="유저 로그인")
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
