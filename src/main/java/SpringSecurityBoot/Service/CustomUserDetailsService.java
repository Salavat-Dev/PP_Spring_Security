package SpringSecurityBoot.Service;

import SpringSecurityBoot.Entity.Role;
import SpringSecurityBoot.Entity.User;
import SpringSecurityBoot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles().stream()
                .map(Role::getName)
                .toArray(String[]::new)));
    }
}
