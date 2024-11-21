package com.asm.tutorCompany.Service;

import com.asm.tutorCompany.Entity.StudentEntity;
import com.asm.tutorCompany.Entity.TutorEntity;
import com.asm.tutorCompany.Entity.UserEntity;
import com.asm.tutorCompany.Repository.StudentRepository;
import com.asm.tutorCompany.Repository.TutorRepository;
import com.asm.tutorCompany.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity updateUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }


    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String name, String email, String password, String userType, String fullName) {
        UserEntity user = new UserEntity();
        user.setUserName(name);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(userType);
        user.setCreatedAt(new Date());
        user = userRepository.save(user);
        if ("ROLE_STUDENT".equals(userType)) {
            StudentEntity student = new StudentEntity();
            student.setName(fullName);
            student.setUser(user);
            studentRepository.save(student);
        } else if ("ROLE_TUTOR".equals(userType)) {
            TutorEntity tutor = new TutorEntity();
            tutor.setUser(user);
            tutorRepository.save(tutor);
        }
    }
    public UserEntity findUserByUserName(String username){
        return userRepository.findUserByUserName(username);
    }

    public List<Map<String, Object>> getMonthlyRegistrationsFor2024() {
        List<Object[]> results = userRepository.findMonthlyRegistrationsFor2024();
        List<Map<String, Object>> monthlyStats = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> stat = new HashMap<>();
            stat.put("month", result[0]);
            stat.put("tutorCount", result[1]);
            stat.put("studentCount", result[2]);
            monthlyStats.add(stat);
        }
        return monthlyStats;
    }
}

