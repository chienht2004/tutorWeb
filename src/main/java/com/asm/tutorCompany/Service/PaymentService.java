package com.asm.tutorCompany.Service;

import com.asm.tutorCompany.Entity.ClassEntity;
import com.asm.tutorCompany.Entity.PaymentEntity;
import com.asm.tutorCompany.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<PaymentEntity> findAll() {
        return paymentRepository.findAll();
    }

    public Optional<PaymentEntity> findById(Integer paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public PaymentEntity save(PaymentEntity paymentEntity) {
        return paymentRepository.save(paymentEntity);
    }

    public void deleteById(Integer paymentId) {
        paymentRepository.deleteById(paymentId);
    }
}
