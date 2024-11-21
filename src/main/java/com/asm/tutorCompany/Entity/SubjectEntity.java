package com.asm.tutorCompany.Entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "Subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subjectId;

    @Column(name = "subjectName", nullable = false, length = 100)
    private String subjectName;

}
