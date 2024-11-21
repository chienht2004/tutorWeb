package com.asm.tutorCompany.Entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true, nullable = false)
    private UserEntity user;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "phoneNumber", length = 15)
    private String phoneNumber;

    @Column(name = "location", length = 100)
    private String location;
}
