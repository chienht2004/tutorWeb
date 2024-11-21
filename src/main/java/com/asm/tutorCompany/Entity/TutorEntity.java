package com.asm.tutorCompany.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Tutors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tutorId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true, nullable = false)
    private UserEntity user;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "phoneNumber", length = 15)
    private String phoneNumber;

    @Column(name = "qualification", columnDefinition = "TEXT")
    private String qualification;

    @Column(name = "location", length = 100)
    private String location;

    @Column(name = "status", length = 20)
    private String status = "active";

}
