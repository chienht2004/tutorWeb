package com.asm.tutorCompany.Controller.LogOut;


import com.asm.tutorCompany.Entity.ClassEntity;
import com.asm.tutorCompany.Service.ClassService;
import com.asm.tutorCompany.Service.SubjectService;
import com.asm.tutorCompany.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    UserService userService;
    @GetMapping
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userService.findUserByUserName(auth.getName()));
            if(userService.findUserByUserName(auth.getName()).getRole().equals("ROLE_ADMIN")){
                return "admin/class";
            }
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "logOut/index";
    }


    @GetMapping("/about")
    public String about(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userService.findUserByUserName(auth.getName()));
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "logOut/about";
    }

    @GetMapping("/blog")
    public String blog(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userService.findUserByUserName(auth.getName()));
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "logOut/blog_details";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userService.findUserByUserName(auth.getName()));
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "logOut/contact";
    }

    @Autowired
    ClassService classService;

    @GetMapping("/courses")
    public String display(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userService.findUserByUserName(auth.getName()));
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        model.addAttribute("grades", classService.getAllDistinctGrades());
        return "logOut/courses";
    }

    @Autowired
    SubjectService subjectService;

    @GetMapping("/courses/detail")
    public String display(Model model,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "9") int size,
                          @RequestParam(required = false) String address,
                          @RequestParam(required = false) Integer subjectId,
                          @RequestParam(required = false) String grade,
                          @RequestParam(defaultValue = "priceAsc") String sortBy,
                          HttpServletRequest request) {

        Page<ClassEntity> classPage = classService.searchAndSortClasses(page, size, address, sortBy, subjectId, grade);
        model.addAttribute("classes", classPage);
        model.addAttribute("address", address);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("grade", grade);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("subjects", subjectService.findAll());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userService.findUserByUserName(auth.getName()));
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        return "logOut/courses_detail";
    }
    @GetMapping("/account/block")
    public String block() {
        return "logOut/account-blocked";
    }

}
