package com.asm.tutorCompany.Service;

import com.asm.tutorCompany.Entity.TutorEntity;
import com.asm.tutorCompany.Entity.UserEntity;
import com.asm.tutorCompany.Repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    @Autowired
    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    public List<TutorEntity> findAll() {
        return tutorRepository.findAll();
    }

    public Optional<TutorEntity> findById(Integer tutorId) {
        return tutorRepository.findById(tutorId);
    }

    public TutorEntity save(TutorEntity tutorEntity) {
        return tutorRepository.save(tutorEntity);
    }

    public void deleteById(Integer tutorId) {
        tutorRepository.deleteById(tutorId);
    }
    public TutorEntity findTutorByUser(UserEntity userEntity){
        return tutorRepository.findTutorByUser(userEntity);
    }
}
