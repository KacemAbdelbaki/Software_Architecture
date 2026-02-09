package com.soft.archi.software_architecture.security.jwt;

import com.soft.archi.software_architecture.entities.UserCredentials;
import com.soft.archi.software_architecture.repositories.IUserRepository;
import com.soft.archi.software_architecture.services.UserCredentialsServiceImpl;
import com.soft.archi.software_architecture.services.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserServiceImpl userService;
    @Autowired
    private UserCredentialsServiceImpl userCredentialsServiceImpl;

    private String parseJwt(HttpServletRequest request) {
        return jwtUtils.getJwtFromCookies(request);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String email = jwtUtils.getBodyFromJwtToken(jwt);
                UserCredentials userCredentials = userCredentialsServiceImpl.findByEmail(email);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userCredentials,
                        null,
                        userCredentials.getAuthorities()
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch(Exception ex){
            log.error("Cannot set user authentication: {}", ex);
        }
        filterChain.doFilter(request, response);
    }
}
