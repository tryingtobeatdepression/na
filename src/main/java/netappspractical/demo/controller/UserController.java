package netappspractical.demo.controller;

import netappspractical.demo.config.AuthenticationFacade;
import netappspractical.demo.domain._User;
import netappspractical.demo.dto.UserDto;
import netappspractical.demo.model.ErrorMessage;
import netappspractical.demo.model.UserObjectResponse;
import netappspractical.demo.repository.RoleRepository;
import netappspractical.demo.repository.UserRepository;
import netappspractical.demo.service.JwtUserDetailsService;
import netappspractical.demo.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser() {
        if(authenticationFacade.getAuthentication().isAuthenticated()) {
            UserDetails userDetails = (UserDetails)authenticationFacade.getAuthentication().getPrincipal();
            _User user = this.userRepository.findByEmail(userDetails.getUsername()).get();
            return ResponseEntity.ok(new UserObjectResponse(user.getName(), user.getEmail(), user.getId()));
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(new Date(),
                Strings.NO_AUTHED_USER));
    }

//    @GetMapping
//    public ResponseEntity<?> getAll() {
//        List<_User> list = this.userRepository.findAll();
//        return ResponseEntity.ok(list.isEmpty() ? Strings.NO_DATA_FOUND: list);
//    }

    @PostMapping("/create")
    public @ResponseBody String create(@RequestBody UserDto userDto) {
        _User user = new _User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRoles(Arrays.asList(this.roleRepository.findByName("ROLE_USER").get()));
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        this.userRepository.save(user);
        return "User created.";
    }
}
