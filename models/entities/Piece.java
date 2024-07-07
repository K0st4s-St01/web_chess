package com.kstoi.web_chess.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Piece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer[] position = new Integer[2];
    private String color;
    private boolean inBag=false;

    @ManyToOne
    @JoinColumn(name = "game")
    private Game game;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    public List<Integer[]> calculateMoves(){
        return switch (name) {
            case "pawn" -> pawn();
            case "rook" -> rook();
            case "horse" -> horse();
            case "assassin" -> assassin();
            case "bishop" -> bishop();
            case "king" -> king();
            case "queen" -> queen();
            default -> List.of();
        };
    }
    private int checkForCollision(Integer[] position){
        for (Piece p : game.getPieces()) {
            var targetPosition = p.getPosition();
            if(Objects.equals(targetPosition[0], position[0])
                    && Objects.equals(targetPosition[1], position[1])){
                if(!Objects.equals(p.getId(), this.getId())){
                    if(!this.getColor().equals(p.color)){
                        return 2;
                    }
                    return  1;
                }
            }
        }
        return 0;
    }

    private List<Integer[]> pawn(){
        var moves = new ArrayList<Integer[]>();
        if(position[0] !=null && position[1] !=null) {
            switch (this.color) {
                case "black":
                    //move one or two front
                    if (0==checkForCollision(new Integer[]{position[0], position[1] + 1}))
                        moves.add(new Integer[]{position[0], position[1] + 1});
                    if (position[1] == 7)
                        if (0==checkForCollision(new Integer[]{position[0], position[1] + 2}))
                            moves.add(new Integer[]{position[0], position[1] + 2});
                    //attack

                    if (2==checkForCollision(new Integer[]{position[0] + 1, position[1] + 1})) {
                        moves.add(new Integer[]{position[0] + 1, position[1] + 1});
                    }
                    if (2==checkForCollision(new Integer[]{position[0] - 1, position[1] + 1})) {
                        moves.add(new Integer[]{position[0] - 1, position[1] + 1});
                    }
                    break;
                case "white":
                    //move one or two front
                    if (0==checkForCollision(new Integer[]{position[0], position[1] - 1}))
                        moves.add(new Integer[]{position[0], position[1] - 1});
                    if (0==checkForCollision(new Integer[]{position[0], position[1] - 2}))
                        if (position[1] == 1)
                            moves.add(new Integer[]{position[0], position[1] - 2});
                    //attack
                    if (2==checkForCollision(new Integer[]{position[0] + 1, position[1] - 1})){
                        moves.add(new Integer[]{position[0] + 1, position[1] - 1});
            }
                    if(2==checkForCollision(new Integer[]{position[0] -1, position[1] - 1})) {
                        moves.add(new Integer[]{position[0] + 1, position[1] - 1});
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + this.color);
            }
        }
        return moves;
    }
    private List<Integer[]> rook(){
        var moves = new ArrayList<Integer[]>();
        RIGHT:for (int i = this.position[0]+1; i < 10; i++) {

            if(1==checkForCollision(new Integer[]{i,position[1]}))
                break;
            if(2==checkForCollision(new Integer[]{i,position[1]})){
                moves.add(new Integer[]{i,position[1]});
                break RIGHT;

            }else {
                moves.add(new Integer[]{i,position[1]});
            }
        }
        LEFT:for (int i = this.position[0]-1; i >= 0; i--) {

            if(1==checkForCollision(new Integer[]{i,position[1]}))
                break ;
            if(2==checkForCollision(new Integer[]{i,position[1]})){
                moves.add(new Integer[]{i,position[1]});
                break LEFT;

            }else {
                moves.add(new Integer[]{i,position[1]});
            }
        }

        DOWN:for (int i = this.position[1]+1; i < 8; i++) {

            if(1==checkForCollision(new Integer[]{position[0],i}))
                break ;
            if(2==checkForCollision(new Integer[]{position[0],i})){
                moves.add(new Integer[]{position[0],i});
                break DOWN;

            }else {
                moves.add(new Integer[]{position[0],i});
            }
        }
        UP:for (int i = this.position[1]-1; i <= 0; i--) {


            if(1==checkForCollision(new Integer[]{position[0],i}))
                break ;
            if(2==checkForCollision(new Integer[]{position[0],i})){
                moves.add(new Integer[]{position[0],i});
                break UP;

            }else {
                moves.add(new Integer[]{position[0],i});
            }
        }
        return moves;
    }
    private List<Integer[]> horse(){
        var moves = new ArrayList<Integer[]>();
        for (int i = -2; i <= 2; i+=4) {
            for (int j = 1; j <= -1; j+=2) {
                int x = position[0]+i;
                int y=position[1]+j;
                if(checkForCollision(new Integer[]{x,y})!=1){
                    moves.add(new Integer[]{x,y});
                }
            }
        }
        for (int i = -1; i <= 1; i+=2) {
            for (int j = -2; j <= 2; j+=4) {
                int x = position[0]+i;
                int y=position[1]+j;
                if(checkForCollision(new Integer[]{x,y})!=1){
                    moves.add(new Integer[]{x,y});
                }
            }
        }
        moves= (ArrayList<Integer[]>) moves.stream().filter(x -> x[0]>=0 && x[0]<10 && x[1]>=0 && x[1]<=8).toList();
        return moves;
    }
    private List<Integer[]> assassin(){
        var moves = new ArrayList<Integer[]>();
        int x=this.position[0];
        int y=this.position[1];
        if(checkForCollision(new Integer[]{x+2,y+2})!=1){
           moves.add(new Integer[]{x+2,y+2});
        }
        if(checkForCollision(new Integer[]{x-2,y+2})!=1){
            moves.add(new Integer[]{x-2,y+2});
        }
        if(checkForCollision(new Integer[]{x+2,y-2})!=1){
            moves.add(new Integer[]{x+2,y-2});
        }
        if(checkForCollision(new Integer[]{x-2,y-2})!=1){
            moves.add(new Integer[]{x-2,y-2});
        }
        moves= (ArrayList<Integer[]>) moves.stream().filter(i -> i[0]>=0 && i[0]<10 && i[1]>=0 && i[1]<=8).toList();
        return moves;
    }
    private List<Integer[]> bishop(){
        var moves = new ArrayList<Integer[]>();
        DOWN_RIGHT:for(int i = this.position[0]+1;i<10;i++)
            for(int j = this.position[1]+1;j<8;j++){

                if(1==checkForCollision(new Integer[]{i,j}))
                    break ;
                if(2==checkForCollision(new Integer[]{i,j})){
                    moves.add(new Integer[]{i,j});
                    break DOWN_RIGHT;

                }else {
                    moves.add(new Integer[]{i,j});
                    i++;
                }
            }
        DOWN_LEFT:for(int i = this.position[0]-1;i>=0;i--)
            for(int j = this.position[1]+1;j<8;j++){
                if(1==checkForCollision(new Integer[]{i,j}))
                    break;
                if(2==checkForCollision(new Integer[]{i,j})){
                    moves.add(new Integer[]{i,j});
                    break DOWN_LEFT;

                }else {
                    moves.add(new Integer[]{i,j});
                    i--;
                }
            }
        UP_RIGHT:for(int i = this.position[0]+1;i<10;i++)
            for(int j = this.position[1]-1;j>=0;j--){
                if(1==checkForCollision(new Integer[]{i,j}))
                    break;
                if(2==checkForCollision(new Integer[]{i,j})){
                    moves.add(new Integer[]{i,j});
                    break UP_RIGHT;

                }else {
                    moves.add(new Integer[]{i,j});
                    i++;
                }
            }
        UP_LEFT:for(int i = this.position[0]-1;i>=0;i--)
            for(int j = this.position[1]-1;j>=0;j--){
                if(1==checkForCollision(new Integer[]{i,j}))
                    break ;
                if(2==checkForCollision(new Integer[]{i,j})){
                    moves.add(new Integer[]{i,j});
                    break UP_LEFT;

                }else {
                    moves.add(new Integer[]{i,j});
                    i--;
                }
            }

        return moves;
    }
    private List<Integer[]> king(){
        var moves = new ArrayList<Integer[]>();
        for (int i = -1; i <=1 ; i++) {
            for(int j=-1; j<=1; j++){
                if(position[0] != position[0]+i || position[1] != position[1]+j){
                    if(checkForCollision(new Integer[]{position[0]+i,position[1]+j})!=1){
                        int x =position[0]+i;
                        int y=position[1]+j;
                        if(x>=0 && x<10 && y>=0 && y<=8){
                            moves.add(new Integer[]{x,y});
                        }
                    }

                }
            }
        }
        return moves;
    }
    private List<Integer[]> queen(){
        var moves = new ArrayList<Integer[]>();
        moves.addAll(rook());
        moves.addAll(bishop());
        return moves;
    }
}
