package com.asm.tutorCompany.Repository;

import com.asm.tutorCompany.Entity.AdminEntity;
import com.asm.tutorCompany.Entity.TutorEntity;
import com.asm.tutorCompany.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    AdminEntity findAdminByUser(UserEntity userEntity);
}
