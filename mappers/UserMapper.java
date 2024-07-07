package com.kstoi.web_chess.mappers;

import com.kstoi.web_chess.models.dtos.UserDto;
import com.kstoi.web_chess.models.entities.Game;
import com.kstoi.web_chess.models.entities.User;
import com.kstoi.web_chess.models.entities.Piece;
import com.kstoi.web_chess.models.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public User toEntity(UserDto dto , List<Piece> pieces, Game game){
        return User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .pieces(pieces)
                .game(game)
                .build();
    }
    public UserDto toDto(User entity,List<Long> pieces,Long game){
        return UserDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .pieces(pieces)
                .game(game)
                .build();
    }
}
