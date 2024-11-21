package com.asm.tutorCompany.Entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "Admins")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adminId;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", unique = true, nullable = false)
    private UserEntity user;

    @Column(name = "fullName", length = 100)
    private String fullName;

    @Column(name = "phoneNumber", length = 15)
    private String phoneNumber;
}
