package SpringSecurityBoot.service;

import SpringSecurityBoot.entity.Role;
import SpringSecurityBoot.entity.User;
import SpringSecurityBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpringSecurityBoot.service.RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User registerUser(String username, String password, String email, boolean isAdmin) {

        Optional<User> existingUser = findByUsername(username);
        if (existingUser.isPresent()) {
            throw new  IllegalArgumentException("Пользователь с таким именем уже существует.");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);

        Role userRole = roleService.findByName("ROLE_USER")
                .orElseGet(() -> roleService.save(new Role("ROLE_USER")));

        if(isAdmin){
            Role adminRole = roleService.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleService.save(new Role("ROLE_ADMIN")));

            user.setRoles(Set.of(adminRole, userRole));
        } else {
            user.setRoles(Set.of(userRole));
        }

        return userRepository.save(user);
    }
}
