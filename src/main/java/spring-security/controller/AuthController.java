package SpringSecurityBoot.controller;

import SpringSecurityBoot.service.RoleService;
import SpringSecurityBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Страница регистрации (GET запрос)
    @GetMapping("/registration")
    public String register() {
        return "registration";  // Отображаем форму регистрации
    }

    // Обработка данных из формы регистрации (POST запрос)
    @PostMapping("/registration")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        try {
            userService.registerUser(username, password, email, false);
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка при регистрации пользователя: " + e.getMessage());
            return "redirect:/registration?error=true";
        } catch (Exception e) {
            System.out.println("Ошибка при регистрации пользователя: " + e.getMessage());
            return "redirect:/registration?error=true";
        }
    }

}

