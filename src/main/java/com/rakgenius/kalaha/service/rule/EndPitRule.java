package com.rakgenius.kalaha.service.rule;

import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.model.GameStatus;
import com.rakgenius.kalaha.model.Pit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EndPitRule extends KalahaGameRule {
    @Override
    public void apply(Game game, Pit endPit) {
        log.debug("checking end rule for the last pit {}", endPit);

        lastEmptyPitRule(game, endPit);
        nextPlayerTurnRule(game, endPit);
        this.next.apply(game, endPit);
    }

    private void lastEmptyPitRule(Game game, Pit endPit){

        if (!endPit.isHouse() && endPit.isPlayerPit(game.getGameStatus()) && endPit.getStoneCount().equals(1) ){
            Pit oppositePit = game.getBoard().getOppositePit(endPit);
            if (oppositePit.getStoneCount() > 0) {
                Pit house = game.getBoard().getPlayerHouse(endPit.getPlayerIndex());
                house.setStoneCount((house.getStoneCount() + oppositePit.getStoneCount()) + endPit.getStoneCount());
                oppositePit.setStoneCount(0);
                endPit.setStoneCount(0);
            }
        }
    }

    private void nextPlayerTurnRule(Game game, Pit endPit){

        if(endPit.isPlayer1House() && game.getGameStatus().equals(GameStatus.PLAYER1_TURN)){
            game.setGameStatus(GameStatus.PLAYER1_TURN);
        }
        else if(endPit.isPlayer2House() && game.getGameStatus().equals(GameStatus.PLAYER2_TURN)){
            game.setGameStatus(GameStatus.PLAYER2_TURN);
        }
        else{
            GameStatus changeStage = game.getGameStatus() == GameStatus.PLAYER1_TURN? GameStatus.PLAYER2_TURN : GameStatus.PLAYER1_TURN;
            game.setGameStatus(changeStage);
        }
    }
}
