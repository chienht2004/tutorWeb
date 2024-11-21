package com.asm.tutorCompany.Security;

import com.asm.tutorCompany.Entity.UserEntity;
import com.asm.tutorCompany.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class BlockedUserFilter extends org.springframework.web.filter.OncePerRequestFilter {

    private final UserRepository userRepository;

    public BlockedUserFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            UserEntity user = userRepository.findById(userDetails.getUser().getUserId()).orElse(null);

            if (user != null && user.isBlocked()) {
                SecurityContextHolder.clearContext();
                response.sendRedirect("/home/account/block");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
