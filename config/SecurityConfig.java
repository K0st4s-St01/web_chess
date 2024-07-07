package com.kstoi.web_chess.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private JAuthenticationProvider authenticationProvider;
    private JwtFilter filter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable());
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests( c -> {
           c.requestMatchers("/user/login","/user","/").permitAll()
                   .anyRequest().authenticated();
        });
        http.formLogin(
                c -> c.disable()
        );
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOriginPatterns(List.of("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedMethods(List.of("*"));
            configuration.setAllowedHeaders(List.of("*"));
            return configuration;
        }));
        http.authenticationProvider(authenticationProvider);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
