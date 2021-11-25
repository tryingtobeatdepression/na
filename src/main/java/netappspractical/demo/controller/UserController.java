package netappspractical.demo.controller;

import netappspractical.demo.domain.User;
import netappspractical.demo.dto.UserDto;
import netappspractical.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody String create(@RequestBody Map<String, String> body) {
        String name = body.get("name"),
                email = body.get("email"),
                password = body.get("password");
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        this.userRepository.save(user);
        return "User created.";
    }
}
