package com.asm.tutorCompany.Repository;

import com.asm.tutorCompany.Entity.TutorEntity;
import com.asm.tutorCompany.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository<TutorEntity, Integer> {
    TutorEntity findTutorByUser(UserEntity userEntity);
}
