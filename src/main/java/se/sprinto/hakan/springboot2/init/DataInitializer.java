package se.sprinto.hakan.springboot2.init;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.sprinto.hakan.springboot2.model.User;
import se.sprinto.hakan.springboot2.repository.UserRepository;

@Component
public class DataInitializer {

    private final UserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository appUserRepository,
                           PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        User user = new User();
        user.setUsername("hakan.gleissman@gmail.com");
        user.setPassword(passwordEncoder.encode("string"));
        user.setEmail("hello@gmail.com");
        user.setRole("ADMIN");
        appUserRepository.save(user);
        System.out.println("Init finished");

    }
}
