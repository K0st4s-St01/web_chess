package com.kstoi.web_chess.config;

import com.kstoi.web_chess.services.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwts = request.getHeader("Authorization");
        if(jwts!= null){
            String user = jwtService.getUser(request);
            var auth = new UsernamePasswordAuthenticationToken(user,null, List.of(new SimpleGrantedAuthority("User")));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }
}
