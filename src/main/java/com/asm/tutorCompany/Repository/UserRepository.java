package com.asm.tutorCompany.Repository;

import com.asm.tutorCompany.Entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUserNameOrEmail(String username, String email);

    UserEntity findUserByUserName(String username);

    @Query(value = """
            SELECT 
                MONTH(u.created_at) AS month,
                SUM(CASE WHEN t.user_id IS NOT NULL THEN 1 ELSE 0 END) AS tutor_count,
                SUM(CASE WHEN s.user_id IS NOT NULL THEN 1 ELSE 0 END) AS student_count
            FROM 
                [users] u
            LEFT JOIN 
                tutors t ON u.user_id = t.user_id
            LEFT JOIN 
                students s ON u.user_id = s.user_id
            WHERE 
                YEAR(u.created_at) = 2024
            GROUP BY 
                MONTH(u.created_at)
            ORDER BY 
                MONTH(u.created_at)
            """, nativeQuery = true)
    List<Object[]> findMonthlyRegistrationsFor2024();
}
