package SpringSecurityBoot.Controller;

import SpringSecurityBoot.Entity.Role;
import SpringSecurityBoot.Entity.User;
import SpringSecurityBoot.Repository.RoleRepository;
import SpringSecurityBoot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.Set;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    // Страница регистрации (GET запрос)
    @GetMapping("/registration")
    public String register() {
        return "registration";  // Отображаем форму регистрации
    }

    // Обработка данных из формы регистрации (POST запрос)
    @PostMapping("/registration")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        try {
            // Логирование для отладки
            System.out.println("Попытка зарегистрировать пользователя: " + username);

            // Проверяем, есть ли уже такой пользователь
            Optional<User> existingUser = userRepository.findByUsername(username);
            if (existingUser.isPresent()) {
                System.out.println("Пользователь с таким именем уже существует.");
                return "redirect:/registration?error=exists"; // Перенаправляем с ошибкой
            }

            // Создаем нового пользователя
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));  // Шифруем пароль
            user.setEmail(email);

            Role userRole = roleRepository.findByName("ROLE_USER");

            if(userRole == null) {
                userRole = new Role();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);
            }

            user.setRoles(Set.of(userRole));

            // Сохраняем нового пользователя в базе данных
            userRepository.save(user);


            return "redirect:/login";  // Переадресуем на страницу входа
        } catch (Exception e) {
            System.out.println("Ошибка при регистрации пользователя: " + e.getMessage());
            return "redirect:/registration?error=true";  // Перенаправляем с ошибкой
        }
    }

}

