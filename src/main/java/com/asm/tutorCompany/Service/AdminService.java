package com.asm.tutorCompany.Service;

import com.asm.tutorCompany.Entity.AdminEntity;
import com.asm.tutorCompany.Entity.UserEntity;
import com.asm.tutorCompany.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<AdminEntity> findAll() {
        return adminRepository.findAll();
    }

    public Optional<AdminEntity> findById(Integer adminId) {
        return adminRepository.findById(adminId);
    }

    public AdminEntity save(AdminEntity adminEntity) {
        return adminRepository.save(adminEntity);
    }

    public void deleteById(Integer adminId) {
        adminRepository.deleteById(adminId);
    }
    public AdminEntity findAdminByUserId(UserEntity userEntity){
        return adminRepository.findAdminByUser(userEntity);
    }
}
