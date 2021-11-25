package netappspractical.demo.service;

import netappspractical.demo.domain._User;
import netappspractical.demo.dto.UserDto;
import netappspractical.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername: " + email);
        _User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User not found with email: %s", email));
        }
        return new User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
//
//    /**
//     * Create a new User
//     *
//     * @param userDto
//     * @return
//     */
//    public _User create(UserDto userDto) {
//        _User user = new _User();
//        user.setEmail(userDto.getEmail());
//        user.setName(userDto.getName());
//        user.setPassword(userDto.getPassword());
//        return this.userRepository.save(user);
//    }

}
