package com.asm.tutorCompany.Controller.Student;

import com.asm.tutorCompany.Service.StudentService;
import com.asm.tutorCompany.Service.SubjectService;
import com.asm.tutorCompany.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    @Autowired
    SubjectService subjectService;

    @GetMapping("/class/add")
    public String addClass(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userService.findUserByUserName(auth.getName()));
            model.addAttribute("student", studentService.findStudentByUser(userService.findUserByUserName(auth.getName())));
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        model.addAttribute("subjects", subjectService.findAll());

        return "student/addClass";
    }

}
