package com.jobtracker.job_tracker.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.time.Instant;
import java.util.UUID;

import com.jobtracker.job_tracker.model.RefreshToken;
import com.jobtracker.job_tracker.model.User;
import com.jobtracker.job_tracker.repository.RefreshTokenRepository;
import com.jobtracker.job_tracker.repository.UserRepository;

@Service
public class AuthService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final long refreshTokenDurationMs;

    public AuthService(RefreshTokenRepository refreshTokenRepository,
                       UserRepository userRepository,
                       @Value("${jwt.refresh-token-expiration-ms}") long refreshTokenDurationMs) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.refreshTokenDurationMs = refreshTokenDurationMs;
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        rt.setRevoked(false);
        return refreshTokenRepository.save(rt);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now()) || token.isRevoked()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired or revoked");
        }
        return token;
    }

    public void revokeRefreshToken(RefreshToken token) {
        token.setRevoked(true);
        refreshTokenRepository.save(token);
    }

    public void revokeAllRefreshTokensForUser(User user) {
        refreshTokenRepository.findAllByUser(user).forEach(t -> {
            t.setRevoked(true);
            refreshTokenRepository.save(t);
        });
    }
}
