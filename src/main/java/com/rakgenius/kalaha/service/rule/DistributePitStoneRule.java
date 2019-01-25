package com.rakgenius.kalaha.service.rule;

import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.model.Pit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DistributePitStoneRule extends KalahaGameRule {
    @Override
    public void apply(Game game, Pit currentPit) {
        log.debug("check the rules for distributing stone to the next pit(s)");

        Integer stoneToDistribute = currentPit.getStoneCount();
        currentPit.setStoneCount(0);

        // Distribute the stones
        for (int i = 0; i < stoneToDistribute; i++) {
            currentPit = game.getBoard().getNextPit(currentPit);
            log.debug("next pit {}", currentPit);
            if (currentPit.isDistributable(game.getGameStatus())) {
                currentPit.setStoneCount(currentPit.getStoneCount() + 1);
            }else{
                i--;
            }
        }

        this.next.apply(game, currentPit);
    }
}
