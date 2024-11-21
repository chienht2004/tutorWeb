package com.asm.tutorCompany.Controller.LogOut;


import com.asm.tutorCompany.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String display(Model model) {
        model.addAttribute("listUser", userService.getAllUsers());
        return "logOut/register";
    }

    @PostMapping()
    public String registerUser(@RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               @RequestParam("userType") String userType,
                               @RequestParam("fullName") String fullName,
                               Model model) {
        System.out.println(fullName);
        try {
            userService.registerUser(name, email, password, userType, fullName);
            model.addAttribute("message", "Registration successful.");
            return "logOut/login";
        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("error", "Registration failed.");
            return "logOut/register";
        }
    }

}

