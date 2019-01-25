package com.rakgenius.kalaha.api;

import com.rakgenius.kalaha.model.Game;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GameApiTest {
    private static final Integer INITIAL_STONE_ON_PIT = 6;
    private static final Integer INITIAL_STONE_ON_HOUSE = 0;
    private static final Integer PLAYER1_INDEX = 1;
    private static final Integer PLAYER2_INDEX = 2;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @DirtiesContext
    public void shouldInitGame() throws Exception {

        MockHttpServletRequestBuilder initGameRequest = MockMvcRequestBuilders.post("/api/newgame");

        mockMvc.perform(initGameRequest)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())

                //check game state
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameStatus").value("INIT"))

                //check total pit size
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.size()", Matchers.is(14)))

                //check pit index
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.1.pitIndex").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.8.pitIndex").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.14.pitIndex").value(14))

                //check player index
                .andExpect(MockMvcResultMatchers.jsonPath("$.player1.playerIndex").value(GameApiTest.PLAYER1_INDEX))
                .andExpect(MockMvcResultMatchers.jsonPath("$.player2.playerIndex").value(GameApiTest.PLAYER2_INDEX))

                //check pit owner
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.6.playerIndex").value(GameApiTest.PLAYER1_INDEX))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.13.playerIndex").value(GameApiTest.PLAYER2_INDEX))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.14.playerIndex").value(GameApiTest.PLAYER2_INDEX))

                //check  initial pit stone count
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.5.stoneCount").value(GameApiTest.INITIAL_STONE_ON_PIT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.12.stoneCount").value(GameApiTest.INITIAL_STONE_ON_PIT))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.14.stoneCount").value(GameApiTest.INITIAL_STONE_ON_HOUSE))

                .andReturn();
    }


    @Test
    @DirtiesContext
    public void shouldPlayGame() throws Exception {

        MockHttpServletRequestBuilder initGameRequest = MockMvcRequestBuilders.post("/api/newgame");
        String responseString = mockMvc.perform(initGameRequest).andReturn().getResponse().getContentAsString();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Game game = objectMapper.readValue(responseString, Game.class);


        MockHttpServletRequestBuilder playGame = MockMvcRequestBuilders.put("/api/games/"+game.getId()+"/pits/"+ 1);

        mockMvc.perform(playGame)
                .andExpect(MockMvcResultMatchers.status().isOk())

                //check game id
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))

                //check total pit size
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.size()", Matchers.is(14)))

                //check player index
                .andExpect(MockMvcResultMatchers.jsonPath("$.player1.playerIndex").value(GameApiTest.PLAYER1_INDEX))
                .andExpect(MockMvcResultMatchers.jsonPath("$.player2.playerIndex").value(GameApiTest.PLAYER2_INDEX))


                //starting pit should be zero
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.1.stoneCount").value(0))

                //pit should increment by 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.2.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.3.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.4.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.5.stoneCount").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.6.stoneCount").value(7))

                //player 1 house should increment by 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.board.pitMap.7.stoneCount").value(1))

                //check game state as end with player 1 house
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameStatus").value("PLAYER1_TURN"))

                .andReturn();

    }
}
