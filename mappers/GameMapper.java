package com.kstoi.web_chess.mappers;

import com.kstoi.web_chess.models.dtos.GameDto;
import com.kstoi.web_chess.models.entities.Game;
import com.kstoi.web_chess.models.entities.Piece;
import com.kstoi.web_chess.models.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameMapper {
    public Game toEntity(GameDto dto, List<User> users ,List<Piece> pieces){
        return Game.builder()
                .id(dto.getId()!=null?dto.getId():null)
                .users(users)
                .turn(dto.getTurn())
                .pieces(pieces)
                .build();
    }
    public GameDto toDto(Game entity, List<String> users ,List<Long> pieces){
        return GameDto.builder()
                .id(entity.getId()!=null?entity.getId():null)
                .users(users)
                .turn(entity.getTurn())
                .pieces(pieces)
                .build();
    }
}
