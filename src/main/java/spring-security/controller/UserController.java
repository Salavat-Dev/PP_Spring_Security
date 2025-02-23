package SpringSecurityBoot.controller;

import SpringSecurityBoot.entity.User;
import SpringSecurityBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String userPage(Model model, Authentication authentication) {
        // Получаем имя текущего аутентифицированного пользователя
        String username = authentication.getName();  // Используем getName() для получения имени пользователя

        // Получаем текущего пользователя из базы данных по имени
        Optional<User> currentUser = userRepository.findByUsername(username);

        // Получаем роли текущего пользователя
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Добавляем информацию о текущем пользователе в модель
        model.addAttribute("username", currentUser.get().getUsername());
        model.addAttribute("email", currentUser.get().getEmail());
        model.addAttribute("roles", authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", ")));

        return "user";  // Шаблон user.html
    }
}
