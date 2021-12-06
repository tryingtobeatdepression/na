package netappspractical.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private UserController userController;

    @GetMapping({"/home", "/"})
    public String firstPage() {
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("email", new String(""));
        model.addAttribute("password", new String(""));
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
}
