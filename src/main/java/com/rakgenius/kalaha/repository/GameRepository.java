package com.rakgenius.kalaha.repository;

import com.rakgenius.kalaha.exception.KalahaGameException;
import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.service.GameService;
import com.rakgenius.kalaha.service.MongoDbService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GameRepository {
    private static final Map<Integer, Game> gameMap = new ConcurrentHashMap<>();

    private GameService gameService;
    @Autowired
    MongoDbService mongoDbService;

    @Autowired
    GamesRepository gamesRepository;

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public Game create(Integer initialPitStoneCount){
        // Unique id for the game
        int id = atomicInteger.incrementAndGet();

        // create new game with initial stone count of 6
        Game game = new Game(initialPitStoneCount);
        game.setId(id);
        game.set_id(ObjectId.get());
        gameMap.put(id, game);

        // If connected to mongodb then store in db
        if (mongoDbService.isConnected()) {
            gamesRepository.save(game);
        }
        return gameMap.get(id);
    }

    public Game findById(Integer id){
        Game game = gameMap.get(id);
        if(game == null){
            throw new KalahaGameException("Game is not found for the id: " + id);
        }
        return game;
    }

    public List<Game> getGames() {
        List<Game> result = new ArrayList<>();
        for (Integer key : gameMap.keySet()) {
            result.add(gameMap.get(key));
        }

        return result;
    }

    public void deleteAllGames() {
        // no need for any validations. Just clear it out
        gameMap.clear();
    }

    public Game deleteGameById(int id) {
        Game game = gameMap.get(id);
        if (game == null) {
            throw new KalahaGameException("Game not found for the id: " + id);
        }
        gameMap.remove(id);
        return game;
    }

    @PreDestroy
    public void cleanUpCollection() {
        // if connected to db then clean everything before shutting down application
        if (mongoDbService.isConnected()) {
            gamesRepository.deleteAll();
        }
    }
}
