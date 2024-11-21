package com.asm.tutorCompany.Entity;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @Column(name = "paymentDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    @Column(name = "status", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'Unpaid'")
    private String status;

    public void setStatus(String status) {
        if ("Paid".equals(status)) {
            this.paymentDate = new Date();
        }
        this.status = status;
    }
}
