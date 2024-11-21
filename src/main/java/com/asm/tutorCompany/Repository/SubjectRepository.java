package com.asm.tutorCompany.Repository;

import com.asm.tutorCompany.Entity.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Integer> {
    // You can define custom query methods here if needed
}
