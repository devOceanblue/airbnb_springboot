package com.example.airbnb_clone.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Home", description="홈화면")
@RestController
public class Home {
    @Operation(summary="홈화면", description="홈화면")
    @GetMapping("/")
    public String home(){
        return "시작화면입니다";
    }
}
