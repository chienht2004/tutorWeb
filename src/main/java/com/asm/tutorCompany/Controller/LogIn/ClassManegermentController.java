package com.asm.tutorCompany.Controller.LogIn;

import com.asm.tutorCompany.Entity.*;
import com.asm.tutorCompany.Repository.ClassRepository;
import com.asm.tutorCompany.Service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/all/class")
public class ClassManegermentController {

    @Autowired
    UserService userService;
    @Autowired
    TutorService tutorService;
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;
    @Autowired
    ClassService classService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    PaymentService paymentService;

    @GetMapping()
    public String getClass(Authentication authentication, Model model) {
        String username = authentication.getName();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_STUDENT")) {
                StudentEntity student = studentService.findStudentByUser(userService.findUserByUserName(username));
                model.addAttribute("listClass", classService.findClassEntitiesByStudentId(student));
                model.addAttribute("user", student);
                return "student/allClass";
            } else if (authority.getAuthority().equals("ROLE_TUTOR")) {
                TutorEntity tutor = tutorService.findTutorByUser(userService.findUserByUserName(username));
                model.addAttribute("listClass", classService.findClassEntitiesByTutorId(tutor));
                model.addAttribute("user", tutor);
                return "tutor/allClass";
            }
        }
        return "error";
    }

    @PostMapping("/add")
    public String addClass(
            @RequestParam("subjectId") Integer subjectId,
            @RequestParam("grade") String grade,
            @RequestParam("address") String address,
            @RequestParam("price") float price,
            @RequestParam("description") String description,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam("date") String date,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        StudentEntity student = new StudentEntity();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            student = studentService.findStudentByUser(userService.findUserByUserName(auth.getName()));
        }
        ClassEntity classEntity = new ClassEntity();
        classEntity.setSubjectId(subjectService.findById(subjectId).orElse(null));
        classEntity.setStudentId(student);
        classEntity.setStatus("Inactive");
        classEntity.setGrade(grade);
        classEntity.setAddress(address);
        classEntity.setPrice(price);
        classEntity.setDescription(description);
        classEntity.setStartTime(Time.valueOf(startTime + ":00"));
        classEntity.setEndTime(Time.valueOf(endTime + ":00"));
        classEntity.setDate(date);
        classEntity.setPostDate(new Date());
        classService.save(classEntity);
        model.addAttribute("class", classEntity);
        return "redirect:/all/class";
    }


    @GetMapping("/edit")
    public String editClass(@RequestParam("id") int classId, Model model) {
        ClassEntity classEntity = classService.findById(classId).orElse(null);
        if (classEntity == null) {
            return "redirect:/all/class";
        }
        model.addAttribute("class", classEntity);
        model.addAttribute("subjects", subjectService.findAll());
        return "student/editClass";
    }


    @PostMapping("/edit")
    public String editClass(
            @RequestParam("classId") int classId,
            @RequestParam("subjectId") int subjectId,
            @RequestParam("grade") String grade,
            @RequestParam("address") String address,
            @RequestParam("price") float price,
            @RequestParam("description") String description,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam("date") String date
    ) {
        ClassEntity classEntity = classService.findById(classId).orElse(null);
        if (classEntity != null) {
            classEntity.setSubjectId(subjectService.findById(subjectId).orElse(null));
            classEntity.setGrade(grade);
            classEntity.setAddress(address);
            classEntity.setPrice(price);
            classEntity.setDescription(description);
            classEntity.setStartTime(Time.valueOf(startTime + ":00"));
            classEntity.setEndTime(Time.valueOf(endTime + ":00"));
            classEntity.setDate(date);

            classService.save(classEntity);
        }

        return "redirect:/all/class";
    }

    @GetMapping("/delete")
    public String deleteClass(@RequestParam int id) {
        classService.deleteById(id);
        return "redirect:/all/class";
    }

    @GetMapping("/pay")
    public String pay(@RequestParam("id") Integer classId, Model model) {
        ClassEntity classEntity = classService.findById(classId).orElse(null);
        classEntity.getPaymentId().setStatus("Processing");
        classEntity.getPaymentId().setPaymentDate(new Date());
        paymentService.save(classEntity.getPaymentId());
        classService.save(classEntity);
        return "redirect:/all/class";
    }

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ClassRepository classRepository;
    @GetMapping("/saved")
    public String getSavedClasses(HttpServletRequest request, Model model) {
        String savedClasses = getCookieValue(request, "savedClasses");
        List<ClassEntity> classes = new ArrayList<>();
        if (savedClasses != null && !savedClasses.isEmpty()) {
            String[] classIdsArray = savedClasses.split("\\.");
            List<Integer> classIds = new ArrayList<>();
            for (String id : classIdsArray) {
                try {
                    classIds.add(Integer.parseInt(id.trim()));
                } catch (NumberFormatException e) {
                    System.out.println("ID không hợp lệ: " + id);
                }
            }
            classes = classRepository.findByClassIdIn(classIds);
        }
        model.addAttribute("savedClasses", classes);
        return "tutor/savedClass";
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

