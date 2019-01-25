package com.rakgenius.kalaha.service.rule;

import com.rakgenius.kalaha.exception.KalahaGameIllegalMoveException;
import com.rakgenius.kalaha.model.Board;
import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.model.GameStatus;
import com.rakgenius.kalaha.model.Pit;
import com.rakgenius.kalaha.model.Player;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartPitRule extends KalahaGameRule {
    @Override
    public void apply(Game game, Pit startPit) {
        log.debug("check rules for the start pit {}", startPit);

        checkPlayerTurnRule(game, startPit);
        checkEmptyStartRULE(startPit);
        this.next.apply(game, startPit);
    }

    private void checkPlayerTurnRule(Game game, Pit startPit){
        // If game is not yet started then anyone can start the game
        if(game.getGameStatus().equals(GameStatus.INIT)) {
            GameStatus gameStatus =  startPit.getPlayerIndex().equals(Player.PLAYER1_INDEX) ? GameStatus.PLAYER1_TURN : GameStatus.PLAYER2_TURN;
            game.setGameStatus(gameStatus);
        }

        // check who has to play next
        if((game.getGameStatus().equals(GameStatus.PLAYER1_TURN) && startPit.getPitIndex() >= Board.PLAYER1_HOUSE) ||
                (game.getGameStatus().equals(GameStatus.PLAYER2_TURN) && startPit.getPitIndex() <= Board.PLAYER1_HOUSE)){
            throw new KalahaGameIllegalMoveException("Incorrect pit to play");
        }
    }

    private void checkEmptyStartRULE(Pit startPit){
        if(startPit.getStoneCount() == 0){
            throw new KalahaGameIllegalMoveException("Can not start from empty pit");
        }
    }
}
