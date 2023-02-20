package com.example.airbnb_clone.controllers;

import com.example.airbnb_clone.Model.User;
import com.example.airbnb_clone.Model.UserTokenState;
import com.example.airbnb_clone.request.AuthenticationRequest;
import com.example.airbnb_clone.security.TokenHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;


@Tag(name="Auth", description="권한, 인증")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    TokenHelper tokenHelper;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary="Access 토큰 발급", description="유저 로그인")
    @PostMapping("/login")
    public ResponseEntity<?>  login(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws AuthenticationException, IOException {
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String token = String.format("Authorization: Bearer %s",tokenHelper.generateToken(user.getUsername()));
        return ResponseEntity.ok(token);
    }


    @Operation(summary="Refresh 토큰 발급", security = { @SecurityRequirement(name = "bearer-key") })
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)

    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request,
            HttpServletResponse response,
            Principal principal) {

        String authToken = tokenHelper.getToken(request);

        if (authToken != null && principal != null) {

            // TODO check user password last update
            String refreshedToken = tokenHelper.refreshToken(authToken);

            return ResponseEntity.ok(refreshedToken);
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }
}
