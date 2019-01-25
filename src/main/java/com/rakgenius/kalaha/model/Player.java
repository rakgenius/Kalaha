package com.rakgenius.kalaha.model;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Player {
    public static final Integer PLAYER1_INDEX = 1;
    public static final Integer PLAYER2_INDEX = 2;

    @NotNull
    private Integer playerIndex;

    @NotNull
    private String name;
}
