package com.jobtracker.job_tracker.repository;

import com.jobtracker.job_tracker.model.RefreshToken;
import com.jobtracker.job_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findAllByUser(User user);
    void deleteByUser(User user);
}
