package netappspractical.demo.controller;

import netappspractical.demo.domain._User;
import netappspractical.demo.dto.UserDto;
import netappspractical.demo.repository.UserRepository;
import netappspractical.demo.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<_User> list = this.userRepository.findAll();
        return ResponseEntity.ok(list.isEmpty() ? Strings.NO_DATA_FOUND: list);
    }

    @PostMapping("/create")
    public @ResponseBody String create(@RequestBody UserDto userDto) {
        _User user = new _User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        this.userRepository.save(user);
        return "User created.";
    }
}
