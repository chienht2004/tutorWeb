package com.asm.tutorCompany.Service;

import com.asm.tutorCompany.Entity.ClassEntity;
import com.asm.tutorCompany.Entity.StudentEntity;
import com.asm.tutorCompany.Entity.SubjectEntity;
import com.asm.tutorCompany.Entity.TutorEntity;
import com.asm.tutorCompany.Repository.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassService {

    private final ClassRepository classRepository;
    @Autowired
    SubjectService subjectService;

    @Autowired
    public ClassService(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    public List<ClassEntity> findAll() {
        return classRepository.findAll();
    }


    public Optional<ClassEntity> findById(Integer classId) {
        return classRepository.findById(classId);
    }

    public ClassEntity save(ClassEntity classEntity) {
        return classRepository.save(classEntity);
    }

    public void deleteById(Integer classId) {
        classRepository.deleteById(classId);
    }

    public Page<ClassEntity> searchAndSortClasses(int page, int size, String address, String sortBy, Integer subjectId, String grade) {
        Sort sort;
        switch (sortBy) {
            case "priceDesc":
                sort = Sort.by("price").descending();
                break;
            case "postDateAsc":
                sort = Sort.by("postDate").ascending();
                break;
            case "postDateDesc":
                sort = Sort.by("postDate").descending();
                break;
            default:
                sort = Sort.by("price").ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        if (subjectId != null) {
            SubjectEntity subject = subjectService.findById(subjectId).orElse(null);
            if (address != null && !address.isEmpty()) {
                if (grade != null && !grade.isEmpty()) {
                    return classRepository.findByAddressContainingAndSubjectIdAndGradeAndStatus(address, subject, grade, "Inactive", pageable);
                } else {
                    return classRepository.findByAddressContainingAndSubjectIdAndStatus(address, subject, "Inactive", pageable);
                }
            } else {
                if (grade != null && !grade.isEmpty()) {
                    return classRepository.findBySubjectIdAndGradeAndStatus(subject, grade, "Inactive", pageable);
                } else {
                    return classRepository.findBySubjectIdAndStatus(subject, "Inactive", pageable);
                }
            }
        } else {
            if (address != null && !address.isEmpty()) {
                if (grade != null && !grade.isEmpty()) {
                    return classRepository.findByAddressContainingAndGradeAndStatus(address, grade, "Inactive", pageable);
                } else {
                    return classRepository.findByAddressContainingAndStatus(address, "Inactive", pageable);
                }
            } else {
                if (grade != null && !grade.isEmpty()) {
                    return classRepository.findByGradeAndStatus(grade, "Inactive", pageable);
                } else {
                    return classRepository.findByStatus("Inactive", pageable);
                }
            }
        }
    }
    public List<String> getAllDistinctGrades() {
        return classRepository.findAllDistinctGrades();
    }

    public List<ClassEntity> findClassEntitiesByStudentId(StudentEntity studentEntity){
        return classRepository.findClassEntitiesByStudentId(studentEntity);
    }

    public List<ClassEntity> findClassEntitiesByTutorId(TutorEntity tutorEntity){
        return classRepository.findClassEntitiesByTutorId(tutorEntity);
    }
}
