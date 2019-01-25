package com.rakgenius.kalaha.service.rule;

import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.model.Pit;

public abstract class KalahaGameRule {
    protected KalahaGameRule next;
    public abstract void apply(Game game, Pit currentPit);

    public KalahaGameRule setNext(KalahaGameRule next) {
        this.next = next;
        return next;
    }
}
