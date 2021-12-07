package netappspractical.demo.config;

import netappspractical.demo.domain.Privilege;
import netappspractical.demo.domain.Role;
import netappspractical.demo.domain._User;
import netappspractical.demo.repository.PrivilegeRepository;
import netappspractical.demo.repository.RoleRepository;
import netappspractical.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        // Create privileges
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE"),
                writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        // Admin privileges
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);

        // Create roles
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        // Create a new Admin
        Optional<_User> userObj = this.userRepository.findByEmail("admin@mail.com");
        _User admin = userObj.get();
        if (admin == null) {
            admin = new _User();
            admin.setEmail("admin@mail.com");
            admin.setName("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRoles(Arrays.asList(adminRole));
            this.userRepository.save(admin);
        }
        // The app will call this method one time only.
        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
        Optional<Privilege> obj = this.privilegeRepository.findByName(name);
        Privilege privilege = obj.isPresent() ? obj.get() : null;
        if (privilege == null) {
            privilege = new Privilege(name);
            this.privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Optional<Role> obj = this.roleRepository.findByName(name);
        Role role = obj.isPresent() ? obj.get() : null;
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            this.roleRepository.save(role);
        }
        return role;
    }
}
