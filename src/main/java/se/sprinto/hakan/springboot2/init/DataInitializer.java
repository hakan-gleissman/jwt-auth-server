package se.sprinto.hakan.springboot2.init;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import se.sprinto.hakan.springboot2.model.User;
import se.sprinto.hakan.springboot2.repository.UserRepository;

@Component
public class DataInitializer {

    private final UserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER =
            LoggerFactory.getLogger(DataInitializer.class);

    public DataInitializer(UserRepository appUserRepository,
                           PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("DataInitializer.init() körs under uppstart..");
        if (appUserRepository.findAll().isEmpty()) {
            User user = new User();
            user.setUsername("hakan.gleissman@gmail.com");
            user.setPassword(passwordEncoder.encode("password123"));
            user.setEmail("hello@gmail.com");
            user.setRole("ADMIN");
            appUserRepository.save(user);
            System.out.println("Default user saved!");
        } else {
            LOGGER.debug("Default-användare är redan sparad i databasen");
        }

        //


    }
}
