package com.numble;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.numble.model.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class NumbleAPI {
    private Integer nextGameID = 0;
    private final Map<Integer, GameInterface> games = new HashMap<>();

    @GetMapping("/api")
    public String apiDescription() {
        return "return a description of the API in the future";
    }

    @PostMapping("/new_game")
    public ObjectNode newGame() {
        ObjectMapper mapper = new ObjectMapper();
        var result = mapper.createObjectNode();
        result.put("status", "OK");
        result.put("game_id", nextGameID);
        games.put(nextGameID, new Game());
        nextGameID++;
        return result;
    }
}
