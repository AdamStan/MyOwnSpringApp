package deanoffice.security;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow();
    }
    
    public User findUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public void deleteUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(u -> userRepository.delete(u));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // TODO: role is just a string???
    public void updateUser(String username, String password, String role,
            String enabled) {
        User user = userRepository.getById(username);
        if (password.length() < 42) {
            user.setPassword(encoder.encode(password));
        }
        user.setRole(new Role(user.getUsername(), role));
        // TODO: please remove that equals!
        user.setEnable(enabled.equals("on"));
        userRepository.save(user);
    }

    // TODO: role is just a string???
    public void insertUser(String username, String password, String role,
            String enabled) {
        User user = new User(username, encoder.encode(password));
        user.setRole(new Role(username, role));
        // TODO: please remove that equals!
        user.setEnable(enabled.equals("on"));
        userRepository.save(user);
    }

}
