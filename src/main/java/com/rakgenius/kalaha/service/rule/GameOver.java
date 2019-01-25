package com.rakgenius.kalaha.service.rule;

import com.rakgenius.kalaha.model.Board;
import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.model.GameStatus;
import com.rakgenius.kalaha.model.Pit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameOver extends KalahaGameRule {
    @Override
    public void apply(Game game, Pit currentPit) {
        log.debug("checking game end rule");

        Integer player1StoneCount = game.getBoard().getPlayer1PitStoneCount();
        Integer player2StoneCount = game.getBoard().getPlayer2PitStoneCount();

        // If either player's all pits are 0 then game has ended. Find out the winner
        if( player1StoneCount == 0 || player2StoneCount == 0){

            game.setGameStatus(GameStatus.FINISHED);

            // Get the total house pit count
            Pit house1 = game.getBoard().getPitMap().get(Board.PLAYER1_HOUSE);
            house1.setStoneCount(house1.getStoneCount() + player1StoneCount);

            Pit house2 = game.getBoard().getPitMap().get(Board.PLAYER2_HOUSE);
            house2.setStoneCount(house2.getStoneCount() + player2StoneCount);

            determineResult(game, house1.getStoneCount(), house2.getStoneCount());

            resetBoard(game);
        }
    }

    private void resetBoard(Game game){
        for(Integer key: game.getBoard().getPitMap().keySet()){
            if(key.equals(Board.PLAYER1_HOUSE) || key.equals(Board.PLAYER2_HOUSE)) {
                continue;
            }
            Pit pit = game.getBoard().getPitMap().get(key);
            pit.setStoneCount(0);
        }
    }

    /**
     * Whoever has the highest house stone count is the winner
     * @param game
     * @param house1StoneCount
     * @param house2StoneCount
     */
    private void determineResult(Game game, Integer house1StoneCount, Integer house2StoneCount){
        if(house1StoneCount > house2StoneCount){
            game.setWinner(game.getPlayer1());
        }else if(house1StoneCount < house2StoneCount){
            game.setWinner(game.getPlayer2());
        }else{
            game.setWinner(null);
        }
    }
}
