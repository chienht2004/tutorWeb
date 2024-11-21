package com.asm.tutorCompany.Entity;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "Tutor_Subjects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TutorSubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tutorSubjectEntityId;

    @Column(name = "tutorId")
    private Integer tutorId;

    @Column(name = "subjectId")
    private Integer subjectId;

    @ManyToOne
    @JoinColumn(name = "tutorId", insertable = false, updatable = false)
    private TutorEntity tutor;

    @ManyToOne
    @JoinColumn(name = "subjectId", insertable = false, updatable = false)
    private SubjectEntity subject;
}

