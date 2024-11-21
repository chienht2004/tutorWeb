package com.asm.tutorCompany.Entity;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "Classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classId;

    @ManyToOne
    @JoinColumn(name = "tutorId")
    private TutorEntity tutorId;

    @ManyToOne
    @JoinColumn(name = "subjectId", nullable = false)
    private SubjectEntity subjectId;

    @OneToOne
    @JoinColumn(name = "paymentId")
    private PaymentEntity paymentId;

    @ManyToOne
    @JoinColumn(name = "studentId", nullable = false)
    private StudentEntity studentId;
    
    @Column(name = "grade", nullable = false, length = 100)
    private String grade;

    @Column(name = "price", nullable = false, length = 100)
    private float price;

    @Column(name = "postDate", nullable = false)
    private Date postDate;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "class_status", length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'Inactive'")
    private String status;

    @Column(name = "date", length = 200)
    private String date;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

}
