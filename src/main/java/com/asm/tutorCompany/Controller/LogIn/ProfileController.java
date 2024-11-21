package com.asm.tutorCompany.Controller.LogIn;

import com.asm.tutorCompany.Cloud.CloudinaryService;
import com.asm.tutorCompany.Entity.AdminEntity;
import com.asm.tutorCompany.Entity.StudentEntity;
import com.asm.tutorCompany.Entity.TutorEntity;
import com.asm.tutorCompany.Entity.UserEntity;
import com.asm.tutorCompany.Service.AdminService;
import com.asm.tutorCompany.Service.StudentService;
import com.asm.tutorCompany.Service.TutorService;
import com.asm.tutorCompany.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping("/all/profile")
public class ProfileController {

    @Autowired
    UserService userService;
    @Autowired
    TutorService tutorService;
    @Autowired
    AdminService adminService;
    @Autowired
    StudentService studentService;

    @GetMapping()
    public String getProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_STUDENT")) {
                StudentEntity student = studentService.findStudentByUser(userService.findUserByUserName(username));
                model.addAttribute("user", student);
                return "student/profile";
            } else if (authority.getAuthority().equals("ROLE_TUTOR")) {
                TutorEntity tutor = tutorService.findTutorByUser(userService.findUserByUserName(username));
                model.addAttribute("user", tutor);
                return "tutor/profile";
            } else if (authority.getAuthority().equals("ROLE_ADMIN")) {
                AdminEntity admin = adminService.findAdminByUserId(userService.findUserByUserName(username));
                model.addAttribute("user", admin);
                return "admin/profile";
            }
        }
        return "error";
    }

    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping("/update/student")
    public String updateStudentProfile(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam("email") String email,
            @RequestParam("userName") String userName,
            @RequestParam("passwordHash") String passwordHash,
            @RequestParam(value = "image", required = false) MultipartFile image,
            RedirectAttributes redirectAttributes) {
        StudentEntity student = studentService.findStudentByUser(userService.findUserByUserName(userName));
        if (student != null) {
            student.setName(name);
            student.setPhoneNumber(phoneNumber);
            student.setLocation(location);
            UserEntity user = student.getUser();
            user.setUserName(userName);
            user.setPasswordHash(passwordHash);
            user.setEmail(email);
            if (image != null && !image.isEmpty()) {
                try {
                    Map<String, String> uploadResult = cloudinaryService.upLoadFile(image);
                    String imageUrl = uploadResult.get("url");
                    user.setProfileImage(imageUrl);
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Image upload failed.");
                    return "redirect:/all/profile";
                }
            }
            userService.updateUser(user);
            studentService.updateStudent(student);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Student not found.");
        }

        return "redirect:/all/profile";
    }

    @PostMapping("/update/tutor")
    public String updateTutorProfile(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam("email") String email,
            @RequestParam(value="qualification", required = false) String qualification,
            @RequestParam("userName") String userName,
            @RequestParam("passwordHash") String passwordHash,
            @RequestParam(value = "image", required = false) MultipartFile image,
            RedirectAttributes redirectAttributes) {
        TutorEntity tutor = tutorService.findTutorByUser(userService.findUserByUserName(userName));
        if (tutor != null) {
            tutor.setName(name);
            tutor.setPhoneNumber(phoneNumber);
            tutor.setLocation(location);
            tutor.setQualification(qualification);
            UserEntity user = tutor.getUser();
            user.setUserName(userName);
            user.setPasswordHash(passwordHash);
            user.setEmail(email);
            if (image != null && !image.isEmpty()) {
                try {
                    Map<String, String> uploadResult = cloudinaryService.upLoadFile(image);
                    String imageUrl = uploadResult.get("url");
                    user.setProfileImage(imageUrl);
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("errorMessage", "Image upload failed.");
                    return "redirect:/all/profile";
                }
            }
            userService.updateUser(user);
            tutorService.save(tutor);
            redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Student not found.");
        }

        return "redirect:/all/profile";
    }
}
