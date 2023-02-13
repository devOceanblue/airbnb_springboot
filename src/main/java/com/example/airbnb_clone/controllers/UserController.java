package com.example.airbnb_clone.controllers;

import com.example.airbnb_clone.Model.User;
import com.example.airbnb_clone.request.AuthenticationRequest;
import com.example.airbnb_clone.security.TokenHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@Tag(name="Auth", description="권한, 인증")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    TokenHelper tokenHelper;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary="로그인", description="유저 로그인")
    @PostMapping("/login")
    public ResponseEntity<?>  login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException, IOException {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String token = tokenHelper.generateToken(user.getUsername());
        return ResponseEntity.ok(token);
    }
}
