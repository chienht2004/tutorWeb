package com.asm.tutorCompany.Repository;

import com.asm.tutorCompany.Entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Integer> {

    Page<ClassEntity> findByStatus(String status, Pageable pageable);

    Page<ClassEntity> findByAddressContainingAndStatus(String address, String status, Pageable pageable);

    Page<ClassEntity> findByAddressContainingAndSubjectIdAndStatus(String address, SubjectEntity subjectId, String status, Pageable pageable);

    Page<ClassEntity> findBySubjectIdAndStatus(SubjectEntity subjectId, String status, Pageable pageable);

    Page<ClassEntity> findByGradeAndStatus(String grade, String status, Pageable pageable);

    Page<ClassEntity> findByAddressContainingAndGradeAndStatus(String address, String grade, String status, Pageable pageable);

    Page<ClassEntity> findByAddressContainingAndSubjectIdAndGradeAndStatus(String address, SubjectEntity subjectId, String grade, String status, Pageable pageable);

    Page<ClassEntity> findBySubjectIdAndGradeAndStatus(SubjectEntity subjectId, String grade, String status, Pageable pageable);

    @Query("SELECT DISTINCT c.grade FROM ClassEntity c WHERE c.status = 'Inactive'")
    List<String> findAllDistinctGrades();

    List<ClassEntity> findClassEntitiesByStudentId(StudentEntity studentEntity);

    List<ClassEntity> findClassEntitiesByTutorId(TutorEntity tutorEntity);

    List<ClassEntity> findByClassIdIn(List<Integer> classIds);
}
