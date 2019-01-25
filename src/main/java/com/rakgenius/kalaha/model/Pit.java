package com.rakgenius.kalaha.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pit {
    @NotEmpty
    private Integer pitIndex;

    @NotNull
    private Integer stoneCount;

    @NotNull
    private Integer playerIndex;

    public Boolean isDistributable(GameStatus gameStatus){

        return (!gameStatus.equals(GameStatus.PLAYER1_TURN) || !this.pitIndex.equals(Board.PLAYER2_HOUSE))
                && (!gameStatus.equals(GameStatus.PLAYER2_TURN) || !this.pitIndex.equals(Board.PLAYER1_HOUSE));
    }

    public Boolean isPlayerPit(GameStatus gameStatus){

        if(gameStatus.equals(GameStatus.PLAYER1_TURN) && this.playerIndex.equals(Player.PLAYER1_INDEX)) {
            return true;
        }else if(gameStatus.equals(GameStatus.PLAYER2_TURN) && this.playerIndex.equals(Player.PLAYER2_INDEX)) {
            return true;
        }

        return false;
    }

    public Boolean isHouse(){
        return this.pitIndex.equals(Board.PLAYER1_HOUSE) || this.pitIndex.equals(Board.PLAYER2_HOUSE);
    }

    public Integer nextPitIndex(){
        Integer index = this.pitIndex + 1;
        if(index > Board.PLAYER2_HOUSE) {
            index = 1;
        }
        return index;
    }

    public Integer getOppositePitIndex(){
        return  (Board.PIT_START_INDEX + Board.PIT_END_INDEX - 1) - this.getPitIndex();
    }

    public Boolean isPlayer1House(){
        return this.playerIndex.equals(Player.PLAYER1_INDEX) && this.pitIndex.equals(Board.PLAYER1_HOUSE);

    }

    public Boolean isPlayer2House(){
        return this.playerIndex.equals(Player.PLAYER2_INDEX) && this.pitIndex.equals(Board.PLAYER2_HOUSE);
    }
}
