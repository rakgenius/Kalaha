package com.rakgenius.kalaha.service;

import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.model.Pit;
import com.rakgenius.kalaha.service.rule.KalahaGameRule;
import com.rakgenius.kalaha.service.rule.DistributePitStoneRule;
import com.rakgenius.kalaha.service.rule.*;
import org.springframework.stereotype.Component;
import com.rakgenius.kalaha.service.rule.*;

@Component
public class GameEngine {
    private final KalahaGameRule chain;
    public GameEngine() {

        this.chain = new StartPitRule();
        chain.setNext(new DistributePitStoneRule())
                .setNext(new EndPitRule())
                .setNext(new GameOver());
    }

    public void play(Game game, Pit pit) {
        this.chain.apply(game, pit);
    }
}
