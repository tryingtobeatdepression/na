package netappspractical.demo.service;

import netappspractical.demo.domain.Privilege;
import netappspractical.demo.domain.Role;
import netappspractical.demo.domain._User;
import netappspractical.demo.dto.UserDto;
import netappspractical.demo.repository.RoleRepository;
import netappspractical.demo.repository.UserRepository;
import netappspractical.demo.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Why user @Transactional?
     * https://stackoverflow.com/questions/3519059/
     * hibernate-failed-to-lazily-initialize-a-collection-of-role-no-session-or-sessi
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<_User> optionalUser = userRepository.findByEmail(email);
        _User user = optionalUser.get();
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User not found with email: %s", email));
        }
        return new User(
                user.getEmail(),
                user.getPassword(),
                getAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    /**
     * TODO: Why this works this way??
     * @param roles
     * @return
     */
    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName()); // Adding all the roles
            collection.addAll(role.getPrivileges()); // Adding all the privileges
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
