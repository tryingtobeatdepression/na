package netappspractical.demo.controller;

import netappspractical.demo.domain._User;
import netappspractical.demo.dto.UserDto;
import netappspractical.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/reg")
    public ModelAndView displayRegForm(WebRequest req, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return new ModelAndView("reg");
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
