package SpringSecurityBoot.Controller;

import SpringSecurityBoot.Entity.Role;
import SpringSecurityBoot.Entity.User;
import SpringSecurityBoot.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
public class AdminController {

    private UserRepository userRepository;

    @Autowired
    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Authentication auth) {

        String username = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        model.addAttribute("username", username);
        model.addAttribute("roles", authorities);

        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);

        return "admin";

    }

}

