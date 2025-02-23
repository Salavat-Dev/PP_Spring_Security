package SpringSecurityBoot.service;

import SpringSecurityBoot.entity.Role;
import SpringSecurityBoot.entity.User;
import SpringSecurityBoot.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    private UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Используем EntityManager для выполнения кастомного запроса
        User user = entityManager.createQuery(
                        "SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().stream()
                .map(Role::getName)
                .toArray(String[]::new)));
    }
}
