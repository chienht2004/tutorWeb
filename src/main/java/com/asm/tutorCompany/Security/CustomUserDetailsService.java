package com.asm.tutorCompany.Security;

import com.asm.tutorCompany.Entity.UserEntity;
import com.asm.tutorCompany.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail);
        }

        if (user.isBlocked()) {
            throw new DisabledException("User account is blocked.");
        }

        return new CustomUserDetails(user);
    }
}
