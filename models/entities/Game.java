package com.kstoi.web_chess.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "game")
    private List<User> users = new ArrayList<>();
    @OneToMany(mappedBy = "game" ,cascade = CascadeType.ALL)
    private List<Piece> pieces = new ArrayList<>();
    private int turn=0;

    public void initialize(){
        User white = users.get(0);
        User black = users.get(1);

        for (int i = 0; i < 10; i++) {
            pieces.add(
                    Piece.builder()
                            .game(this)
                            .color("white")
                            .name("pawn")
                            .user(white)
                            .position(new Integer[]{i,7})
                            .inBag(false)
                    .build()
            );
            pieces.add(
                    Piece.builder()
                            .game(this)
                            .color("black")
                            .name("pawn")
                            .user(black)
                            .position(new Integer[]{i,1})
                            .inBag(false)
                            .build()
            );
        }
        var positions = new Integer[]{0,1,2,3,4,5,6,7,8,9};
        var types = new String[]{"rook","horse","assassin","bishop","queen","king","bishop","assassin","horse","rook"};
        for(Integer pos : positions){
            pieces.add(
                    Piece.builder()
                            .game(this)
                            .color("black")
                            .name(types[pos])
                            .user(black)
                            .position(new Integer[]{pos,0})
                            .inBag(false)
                            .build()
            );

            pieces.add(
                    Piece.builder()
                            .game(this)
                            .color("white")
                            .name(types[pos])
                            .user(white)
                            .position(new Integer[]{pos,8})
                            .inBag(false)
                            .build()
            );
        }

    }

    public Map<String,String> representation(){
        Map<String,String> representation=new HashMap<>();
        for(Piece p : pieces){
            if(!p.isInBag())
            representation.put(p.getPosition()[0]+","+p.getPosition()[1],p.getColor()+"."+p.getName());
        }
        return representation;

    }


}
