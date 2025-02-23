package SpringSecurityBoot.config;

import SpringSecurityBoot.service.RoleService;
import SpringSecurityBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Init implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.findByUsername("admin").isEmpty()) {
            userService.registerUser("admin", "admin", "admin@admin.com", true);
        }

        if(userService.findByUsername("user").isEmpty()){
            userService.registerUser("user", "user", "user@user.com", false);
        }
    }
}
