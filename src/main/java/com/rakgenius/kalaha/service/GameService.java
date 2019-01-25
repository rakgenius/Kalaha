package com.rakgenius.kalaha.service;

import com.rakgenius.kalaha.model.Game;

public interface GameService {
    Game initGame(Integer pitNumber);

    Game play(int gameId, Integer pitId);

}
