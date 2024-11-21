package com.asm.tutorCompany.Controller.Admin;

import com.asm.tutorCompany.Entity.ClassEntity;
import com.asm.tutorCompany.Entity.UserEntity;
import com.asm.tutorCompany.Repository.UserRepository;
import com.asm.tutorCompany.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    @Autowired
    TutorService tutorService;
    @Autowired
    ClassService classService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping()
    public String get(Model model) {
        List<Map<String, Object>> monthlyRegistrations = userService.getMonthlyRegistrationsFor2024();

        List<String> labels = new ArrayList<>();
        List<Integer> studentRegistrations = new ArrayList<>();
        List<Integer> tutorRegistrations = new ArrayList<>();

        for (Map<String, Object> registration : monthlyRegistrations) {
            System.out.println(registration);
            Integer monthIndex = (Integer) registration.get("month");
            String monthName = getMonthName(monthIndex);
            labels.add(monthName);
            studentRegistrations.add((Integer) registration.get("studentCount"));
            tutorRegistrations.add((Integer) registration.get("tutorCount"));
        }

        model.addAttribute("labels", labels);
        model.addAttribute("studentRegistrations", studentRegistrations);
        model.addAttribute("tutorRegistrations", tutorRegistrations);

        model.addAttribute("totalClass", classService.findAll().size());
        model.addAttribute("totalTutor", tutorService.findAll().size());
        model.addAttribute("totalStudent", studentService.getAllStudents().size());

        model.addAttribute("classes", classService.findAll());

        model.addAttribute("totalUnpaidFee", totalUnpaidFee());
        model.addAttribute("totalPaidFee", totalPaidFee());
        model.addAttribute("totalFee", totalUnpaidFee() + totalPaidFee());
        return "admin/class";
    }

    private double totalUnpaidFee() {
        double total = 0;
        for (ClassEntity c : classService.findAll()) {
            if (c.getPrice() != 0 && c.getPaymentId() != null && c.getPaymentId().getStatus().equals("Unpaid")) {
                total += (c.getPrice() * 8 * 0.4);
            }
        }
        return Math.round(total * 10.0) / 10.0;
    }

    private double totalPaidFee() {
        double total = 0;
        for (ClassEntity c : classService.findAll()) {
            if (c.getPrice() != 0 && c.getPaymentId() != null && c.getPaymentId().getStatus().equals("Paid")) {
                total += (c.getPrice() * 8 * 0.4);
            }
        }
        return Math.round(total * 10.0) / 10.0;
    }

    private String getMonthName(Integer monthIndex) {
        String[] monthNames = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        return monthNames[monthIndex - 1];
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam(name = "classId") int classId) {
        classService.findById(classId).orElse(null).getPaymentId().setStatus("Paid");
        classService.save(classService.findById(classId).orElse(null));
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(name = "classId") int classId) {
        classService.deleteById(classId);
        return "redirect:/admin";
    }
    @GetMapping("/users")
    public String getUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("totalClass", classService.findAll().size());
        model.addAttribute("totalTutor", tutorService.findAll().size());
        model.addAttribute("totalStudent", studentService.getAllStudents().size());
        return "admin/user";
    }

    @PostMapping("/block")
    public String blockUser(@RequestParam Integer userId, @RequestParam boolean block) {
        System.out.println(userId);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        user.setBlocked(block);
        userRepository.save(user);

        return "redirect:/admin/users";
    }
}

