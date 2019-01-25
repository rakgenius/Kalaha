package com.rakgenius.kalaha.controller;

import com.rakgenius.kalaha.exception.KalahaGameException;
import com.rakgenius.kalaha.exception.KalahaGameIllegalMoveException;
import com.rakgenius.kalaha.model.Board;
import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.repository.GameRepository;
import com.rakgenius.kalaha.repository.GamesRepository;
import com.rakgenius.kalaha.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class KalahaGameController {
    private final GameService gameService;

    @Autowired
    GamesRepository gamesRepository;
    @Autowired
    GameRepository gameRepository;

    public KalahaGameController(GameService gameService) {
        this.gameService = gameService;
    }

    // Controller to create new game
    @PostMapping(value = "/newgame")
    public ResponseEntity initBoard(@RequestParam(name = "stone", defaultValue = "6", required = false) Integer numberOfStone){
        log.info("Initializing the game with number of stones= {}",numberOfStone );
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.initGame(numberOfStone));
    }

    // Controller to get list of all games
    @GetMapping("/games")
    public ResponseEntity getAllGames() {
        return ResponseEntity.ok().body(gameRepository.getGames());
    }

    @GetMapping("/games/{id}")
    public ResponseEntity getGameById(@PathVariable int id) {
        return ResponseEntity.ok().body(gameRepository.findById(id));
    }

    // Controller to play the game
    @PutMapping("/games/{gameId}/pits/{pitIndex}")
    public ResponseEntity play(@PathVariable int gameId, @PathVariable Integer pitIndex){
        // pit index has to be between 1 and 14
        if(pitIndex > Board.PIT_END_INDEX || pitIndex < Board.PIT_START_INDEX){
            throw new KalahaGameException("Incorrect pit index");
        }

        // User cannot play from house pit
        if(pitIndex.equals(Board.PLAYER1_HOUSE) || pitIndex.equals(Board.PLAYER2_HOUSE)){
            throw new KalahaGameIllegalMoveException("House stone is not allow to distribute");
        }

        return ResponseEntity.ok().body(gameService.play(gameId, pitIndex));
    }

    // Controller to delete all games
    @DeleteMapping("/games/delete/all")
    public ResponseEntity deleteAllGames() {
        gameRepository.deleteAllGames();
        gamesRepository.deleteAll();
        return ResponseEntity.ok().body("All games deleted successfully");
    }

    @DeleteMapping("/games/delete/{id}")
    public ResponseEntity deleteGameById(@PathVariable int id) {
        Game game = gameRepository.deleteGameById(id);
        gamesRepository.deleteById(id);
        return ResponseEntity.ok().body(game);
    }
}
