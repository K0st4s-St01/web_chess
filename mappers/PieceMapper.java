package com.kstoi.web_chess.mappers;

import com.kstoi.web_chess.models.dtos.PieceDto;
import com.kstoi.web_chess.models.entities.Game;
import com.kstoi.web_chess.models.entities.Piece;
import com.kstoi.web_chess.models.entities.User;
import org.springframework.stereotype.Component;

@Component
public class PieceMapper {
    public Piece toEntity(PieceDto dto , Game game, User user){
        return Piece.builder()
                .id(dto.getId()!=null?dto.getId():null)
                .inBag(dto.isInBag())
                .game(game)
                .position(dto.getPosition())
                .user(user)
                .name(dto.getName())
                .color(dto.getColor())
                .build();
    }
    public PieceDto toDto(Piece entity ,Long game ,String user){
        return PieceDto.builder()
                .id(entity.getId()!=null?entity.getId():null)
                .inBag(entity.isInBag())
                .game(game)
                .position(entity.getPosition())
                .user(user)
                .name(entity.getName())
                .color(entity.getColor())
                .build();
    }
}
