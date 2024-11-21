package com.asm.tutorCompany.Repository;

import com.asm.tutorCompany.Entity.ClassEntity;
import com.asm.tutorCompany.Entity.PaymentEntity;
import com.asm.tutorCompany.Entity.TutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
}
