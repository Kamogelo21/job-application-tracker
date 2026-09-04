package com.jobtracker.job_tracker.seed;

import com.jobtracker.job_tracker.model.Role;
import com.jobtracker.job_tracker.model.User;
import com.jobtracker.job_tracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DevDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DevDataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            if (!userRepository.existsByUsername("dev")) {
                User u = new User();
                u.setUsername("dev");
                u.setPassword(passwordEncoder.encode("devpass"));
                u.getRoles().add(Role.ROLE_USER);
                userRepository.save(u);
                System.out.println("Seeded dev user: dev / devpass");
            }
        } catch (Exception e) {
            System.err.println("DevDataLoader failed: " + e.getMessage());
        }
    }
}
