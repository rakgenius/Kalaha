package com.rakgenius.kalaha.service;

import com.rakgenius.kalaha.model.Board;
import com.rakgenius.kalaha.model.Game;
import com.rakgenius.kalaha.model.GameStatus;
import com.rakgenius.kalaha.model.Pit;
import com.rakgenius.kalaha.model.Player;
import com.rakgenius.kalaha.repository.GameRepository;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GameServiceTest {
    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private GameEngine gameEngine;

    @Autowired
    private GameService gameService;

    @Test
    public void shouldInitGame(){
        int id = 1;
        Player player1 = new Player(Player.PLAYER1_INDEX, "Player 1");
        Player player2 = new Player(Player.PLAYER2_INDEX, "Player 2");

        Board board = new Board();
        board.setPitMap(initPit());

        Game game = new Game(Board.INITIAL_STONE_ON_PIT);
        game.setId(id);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setBoard(board);

        //given
        BDDMockito.given(gameRepository.create(BDDMockito.any())).willReturn(game);

        //when
        Game mockGame = gameService.initGame(6);

        //then
        Assert.assertEquals(game, mockGame);

    }


    @Test
    public void shouldPlayGame(){

        Player player1 = new Player(Player.PLAYER1_INDEX, "Player 1");
        Player player2 = new Player(Player.PLAYER2_INDEX, "Player 2");

        Board board = new Board();
        board.setPitMap(initPit());

        int id = 1;
        Game game = new Game(Board.INITIAL_STONE_ON_PIT);
        game.setGameStatus(GameStatus.INIT);
        game.setId(id);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setBoard(board);

        //given1
        BDDMockito.given(gameRepository.findById(id)).willReturn(game);

        //given2
        game.setGameStatus(GameStatus.PLAYER1_TURN);
        BDDMockito.given(gameRepository.create(BDDMockito.any())).willReturn(game);

        //when
        Game mockGame =  gameService.play(game.getId(), game.getBoard().getPitMap().get(1).getPitIndex());

        //then
        Assert.assertEquals(GameStatus.PLAYER1_TURN, mockGame.getGameStatus());
    }


    private Map<Integer, Pit> initPit(){
        Map<Integer, Pit> pits = new HashMap<>();
        for(int i = Board.PIT_START_INDEX; i < Board.PLAYER1_HOUSE; i++){
            Pit pit = new Pit(i, Board.INITIAL_STONE_ON_PIT, Player.PLAYER1_INDEX);
            pits.put(i, pit);
        }
        Pit house1 = new Pit(Board.PLAYER1_HOUSE, Board.INITIAL_STONE_ON_HOUSE, Player.PLAYER1_INDEX);
        pits.put(Board.PLAYER1_HOUSE, house1);

        for(int i= Board.PLAYER1_HOUSE + 1; i < Board.PLAYER2_HOUSE; i++){
            Pit pit = new Pit(i, Board.INITIAL_STONE_ON_PIT, Player.PLAYER2_INDEX);
            pits.put(i, pit);
        }
        Pit house2 = new Pit(Board.PLAYER2_HOUSE, Board.INITIAL_STONE_ON_HOUSE, Player.PLAYER2_INDEX);
        pits.put(Board.PLAYER2_HOUSE, house2);

        return pits;
    }
}
