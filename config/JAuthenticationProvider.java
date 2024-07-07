package com.kstoi.web_chess.config;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import com.kstoi.web_chess.models.dtos.UserDto;
import com.kstoi.web_chess.services.UserService;

@Component
@AllArgsConstructor
public class JAuthenticationProvider implements AuthenticationProvider {
    private UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication){
        String username = authentication.getName();
        UserDto user = (UserDto) userService.read(username).get("dtos");
            if(BCrypt.checkpw(
                    authentication.getCredentials().toString(),
                    user.getPassword()
            )){
                return new UsernamePasswordAuthenticationToken(username,user.getPassword());
            }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
