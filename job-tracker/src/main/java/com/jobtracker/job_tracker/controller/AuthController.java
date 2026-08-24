package com.jobtracker.job_tracker.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.jobtracker.job_tracker.repository.UserRepository;
import com.jobtracker.job_tracker.repository.RefreshTokenRepository;
import com.jobtracker.job_tracker.model.User;
import com.jobtracker.job_tracker.model.Role;
import com.jobtracker.job_tracker.model.RefreshToken;
import com.jobtracker.job_tracker.security.JwtService;
import com.jobtracker.job_tracker.service.AuthService;
import com.jobtracker.job_tracker.dto.*;

import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.existsByUsername(req.username())) {
            return ResponseEntity.badRequest().body("Username already taken");
        }
        User user = new User();
        user.setUsername(req.username());
        user.setPassword(passwordEncoder.encode(req.password()));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = userRepository.findByUsername(request.username()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(user.getUsername(), user.getRoles());
        RefreshToken refreshToken = authService.createRefreshToken(user);

        // set refresh token as HttpOnly cookie (recommended)
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken.getToken())
                .httpOnly(true)
                .path("/auth/refresh")
                .maxAge(60L * 60L * 24L * 30L) // match refresh expiry in seconds — adjust for env
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new JwtResponse(accessToken, null, "Bearer")); // we omit refresh in body because it's cookie
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(name = "refreshToken", required = false) String refreshTokenCookie,
                                     @RequestBody(required = false) TokenRefreshRequest body) {
        String token = refreshTokenCookie != null ? refreshTokenCookie : (body == null ? null : body.refreshToken());
        if (token == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No refresh token provided");

        RefreshToken stored = refreshTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid refresh token"));
        authService.verifyExpiration(stored);
        User user = stored.getUser();

        // Optionally rotate: delete old token and create new one
        authService.revokeRefreshToken(stored);
        RefreshToken newRt = authService.createRefreshToken(user);

        String accessToken = jwtService.generateAccessToken(user.getUsername(), user.getRoles());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", newRt.getToken())
                .httpOnly(true)
                .path("/auth/refresh")
                .maxAge(60L * 60L * 24L * 30L)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new JwtResponse(accessToken, null, "Bearer"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(name = "refreshToken", required = false) String refreshTokenCookie) {
        if (refreshTokenCookie != null) {
            refreshTokenRepository.findByToken(refreshTokenCookie).ifPresent(authService::revokeRefreshToken);
        }
        // Clear cookie
        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .path("/auth/refresh")
                .maxAge(0)
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("Logged out");
    }
}
