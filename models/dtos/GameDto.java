package com.kstoi.web_chess.models.dtos;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameDto {
    private Long id;
    private List<String> users = new ArrayList<>();
    private List<Long> pieces = new ArrayList<>();
    private int turn;

}
