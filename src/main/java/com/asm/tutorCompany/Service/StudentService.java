package com.asm.tutorCompany.Service;

import com.asm.tutorCompany.Entity.StudentEntity;
import com.asm.tutorCompany.Entity.UserEntity;
import com.asm.tutorCompany.Repository.StudentRepository;
import com.asm.tutorCompany.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<StudentEntity> getStudentById(Integer studentId) {
        return studentRepository.findById(studentId);
    }

    public StudentEntity createStudent(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }

    public StudentEntity updateStudent(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }


    public void deleteStudent(Integer studentId) {
        studentRepository.deleteById(studentId);
    }
    public StudentEntity findStudentByUser(UserEntity userEntity){
        return  studentRepository.findStudentByUser(userEntity);
    }

}
