package com.asm.tutorCompany.Repository;

import com.asm.tutorCompany.Entity.StudentEntity;
import com.asm.tutorCompany.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    StudentEntity findStudentByUser(UserEntity userEntity);
}
