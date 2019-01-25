package com.rakgenius.kalaha.controller;

import com.rakgenius.kalaha.exception.KalahaGameException;
import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.repository.GameRepository;
import com.rakgenius.kalaha.repository.GamesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/mongo")
public class MongoDBController {
    @Autowired
    GamesRepository gamesRepository;

    @Autowired
    GameRepository gameRepository;

    @RequestMapping("/games")
    public String findAllGames(Model model) {
        model.addAttribute("gamesList", gamesRepository.findAll());
        return "all_games";
    }

    @RequestMapping("/games/{id}")
    public String findGameById(@PathVariable int id, Model model) {
        model.addAttribute("gamesList", gamesRepository.findById(id));
        return "all_games";
    }

    @RequestMapping("/delete/all")
    public String deleteAllGames(Model model) {
        model.addAttribute("status", "All games deleted successfully");
        gamesRepository.deleteAll();
        return "status";
    }

    @RequestMapping("/delete/{id}")
    public String deleteGameById(@PathVariable("id")int id, Model model) {
        gamesRepository.delete(gameRepository.findById(id));
        model.addAttribute("status", "Game deleted succesfully");
        return "status";
    }

    @GetMapping("/games/load")
    public String getGameId() {
        return "load_game";
    }

    @GetMapping("/games/load/{gameId}")
    public ResponseEntity loadGame(@PathVariable int gameId) {
        log.info("Searching for game with id {}", gameId);
        Game game = gamesRepository.findById(gameId);
        if (game != null) {
            log.info("Successfully found the game with id {}", gameId);
        } else {
            log.error("Unable to find the game with id {}", gameId);
            throw new KalahaGameException("Unable to find the game with id " + gameId);
        }

        return ResponseEntity.ok().body(game);
    }
}
