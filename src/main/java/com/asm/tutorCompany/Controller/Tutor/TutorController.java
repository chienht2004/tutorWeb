package com.asm.tutorCompany.Controller.Tutor;


import com.asm.tutorCompany.Entity.ClassEntity;
import com.asm.tutorCompany.Entity.PaymentEntity;
import com.asm.tutorCompany.Entity.TutorEntity;
import com.asm.tutorCompany.Repository.PaymentRepository;
import com.asm.tutorCompany.Service.ClassService;
import com.asm.tutorCompany.Service.PaymentService;
import com.asm.tutorCompany.Service.TutorService;
import com.asm.tutorCompany.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Date;

@Controller()
@RequestMapping("/tutor")
public class TutorController {
    @Autowired
    ClassService classService;
    @Autowired
    UserService userService;
    @Autowired
    TutorService tutorService;
    @Autowired
    PaymentRepository paymentRepository;
    @GetMapping("/class")
    public String getClassByClassId(@RequestParam("id") int id, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && !auth.getName().equals("anonymousUser")) {
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("user", userService.findUserByUserName(auth.getName()));
        } else {
            model.addAttribute("isAuthenticated", false);
        }
        model.addAttribute("classEnt", classService.findById(id).orElse(null));
        model.addAttribute("student", classService.findById(id).orElse(null).getStudentId());
        return "tutor/class_detail";
    }

    @Autowired
    PaymentService paymentService;
    @PostMapping("/class/register")
    public String registerClass(@RequestParam("classId") int classId, Model model, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        TutorEntity tutor = tutorService.findTutorByUser(userService.findUserByUserName(username));
        ClassEntity classEntity = classService.findById(classId).orElse(null);
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentDate(new Date());
        paymentEntity.setStatus("Unpaid");
        classEntity.setPaymentId(paymentEntity);
        classEntity.setStatus("Active");
        classEntity.setTutorId(tutor);
        paymentService.save(paymentEntity);
        classService.save(classEntity);
        redirectAttributes.addFlashAttribute("message", "Successfully registered for class");
        return "redirect:/home/courses/detail";
    }

}
