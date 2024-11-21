package com.asm.tutorCompany.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "userName", nullable = false, unique = true, length = 50)
    private String userName;

    @Column(name = "passwordHash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "role", length = 20)
    private String role;

    @Column(name = "profileImage", length = 255)
    private String profileImage = "/assets/img/avatar/avatar_default.png";

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "createdAt", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "isBlocked", nullable = false)
    private boolean isBlocked = false; // Giá trị mặc định là không bị chặn
}
