package com.asm.tutorCompany.Repository;

import com.asm.tutorCompany.Entity.TutorSubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorSubjectRepository extends JpaRepository<TutorSubjectEntity, Integer> {
    // You can define custom query methods here if needed
}
