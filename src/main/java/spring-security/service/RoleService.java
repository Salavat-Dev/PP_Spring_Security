package SpringSecurityBoot.service;

import SpringSecurityBoot.entity.Role;
import SpringSecurityBoot.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(String name) {
        Role role = new Role(name);
        return roleRepository.save(role);
    }

    public Optional<Role> findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
