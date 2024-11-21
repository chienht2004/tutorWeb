package com.asm.tutorCompany.Service;

import com.asm.tutorCompany.Entity.TutorSubjectEntity;
import com.asm.tutorCompany.Repository.TutorSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorSubjectService {

    private final TutorSubjectRepository tutorSubjectRepository;

    @Autowired
    public TutorSubjectService(TutorSubjectRepository tutorSubjectRepository) {
        this.tutorSubjectRepository = tutorSubjectRepository;
    }

    public List<TutorSubjectEntity> findAll() {
        return tutorSubjectRepository.findAll();
    }

    public Optional<TutorSubjectEntity> findById(Integer id) {
        return tutorSubjectRepository.findById(id);
    }

    public TutorSubjectEntity save(TutorSubjectEntity tutorSubjectEntity) {
        return tutorSubjectRepository.save(tutorSubjectEntity);
    }

    public void deleteById(Integer id) {
        tutorSubjectRepository.deleteById(id);
    }
}
