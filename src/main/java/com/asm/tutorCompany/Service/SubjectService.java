package com.asm.tutorCompany.Service;

import com.asm.tutorCompany.Entity.SubjectEntity;
import com.asm.tutorCompany.Repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<SubjectEntity> findAll() {
        return subjectRepository.findAll();
    }

    public Optional<SubjectEntity> findById(Integer subjectId) {
        return subjectRepository.findById(subjectId);
    }

    public SubjectEntity save(SubjectEntity subjectEntity) {
        return subjectRepository.save(subjectEntity);
    }

    public void deleteById(Integer subjectId) {
        subjectRepository.deleteById(subjectId);
    }
}
