package com.rakgenius.kalaha.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Game {
    public ObjectId _id;
    @Id
    private int id;
    private Board board;
    private Player player1;
    private Player player2;
    private Player winner;
    private GameStatus gameStatus;

    public Game(Integer initialStoneOnPit) {
        this.player1 = new Player(Player.PLAYER1_INDEX, "player1");
        this.player2 = new Player(Player.PLAYER2_INDEX, "player2");
        this.board = new Board(initialStoneOnPit, player1, player2);
        this.gameStatus = GameStatus.INIT;
    }

    // ObjectId needs to be converted to string
    public String get_id() { return _id.toHexString(); }
    public void set_id(ObjectId _id) { this._id = _id; }
}
