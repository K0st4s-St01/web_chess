package com.kstoi.web_chess.models.dtos;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PieceDto {
    private Long id;
    private String name;
    private Integer[] position = new Integer[2];
    private String color;
    private boolean inBag=false;

    private Long game;
    private String user;

}
