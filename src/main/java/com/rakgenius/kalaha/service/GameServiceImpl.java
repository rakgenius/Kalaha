package com.rakgenius.kalaha.service;

import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.model.Pit;
import com.rakgenius.kalaha.repository.GameRepository;
import com.rakgenius.kalaha.repository.GamesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final GameEngine gameEngine;

    @Autowired
    GamesRepository gamesRepository;
    @Autowired
    MongoDbService mongoDbService;

    public GameServiceImpl(GameRepository gameMemoryRepository, GameEngine gameEngine) {
        this.gameRepository = gameMemoryRepository;
        this.gameEngine = gameEngine;
    }

    @Override
    public Game initGame(Integer initialPitStoneCount) {
        return gameRepository.create(initialPitStoneCount);
    }

    @Override
    public Game play(int gameId, Integer pitIndex) {
        log.debug("gameId {}, pitIndex {}",gameId, pitIndex);

        Game game = gameRepository.findById(gameId);
        Pit pit = game.getBoard().getPitByPitIndex(pitIndex);

        gameEngine.play(game, pit);

        if (mongoDbService.isConnected()) {
            gamesRepository.save(game);
        }
        return  game;
    }

}
