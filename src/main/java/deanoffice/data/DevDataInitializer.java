package deanoffice.data;

import deanoffice.security.Role;
import deanoffice.security.User;
import deanoffice.security.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevDataInitializer implements ApplicationRunner, DataInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DevDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initialize();
    }

    @Override
    public void initialize() {
        String password = passwordEncoder.encode("haslo@123");
        userRepository.save(new User("admin", password, true, Role.ROLE_ADMIN));
        userRepository.save(new User("student0", password, true, Role.ROLE_STUDENT));
        userRepository.save(new User("tutor0", password, true, Role.ROLE_TUTOR));
    }
}
