package com.numble;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.numble.model.Game;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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
        result.put("target_result", games.get(nextGameID).getTargetResult());
        nextGameID++;
        return result;
    }

    @GetMapping("/get_target/{id}")
    public ObjectNode getTarget(@PathVariable int id) {
        if (id < nextGameID) {
            ObjectMapper mapper = new ObjectMapper();
            var result = mapper.createObjectNode();
            result.put("status", "OK");
            result.put("target", games.get(id).getTarget());
            return result;
        } else {
            return makeError(404, "invalid game id");
        }
    }

    @GetMapping("/get_target_result/{id}")
    public ObjectNode getTargetResult(@PathVariable int id) {
        if (id < nextGameID) {
            ObjectMapper mapper = new ObjectMapper();
            var result = mapper.createObjectNode();
            result.put("status", "OK");
            result.put("target_result", games.get(id).getTargetResult());
            return result;
        } else {
            return makeError(404, "invalid game id");
        }
    }

    private ObjectNode makeError(int code, String message) {
        ObjectMapper mapper = new ObjectMapper();
        var result = mapper.createObjectNode();
        result.put("status", "error");
        var error = mapper.createObjectNode();
        error.put("code", code);
        error.put("description", message);
        result.set("error", error);
        return result;
    }

    @GetMapping("/has_won/{id}")
    public ObjectNode hasWon(@PathVariable int id) {
        if (id < nextGameID) {
            ObjectMapper mapper = new ObjectMapper();
            var result = mapper.createObjectNode();
            result.put("status", "OK");
            result.put("has_won", games.get(id).hasWon());
            return result;
        } else {
            return makeError(404, "invalid game id");
        }
    }

    @GetMapping("/target_length/{id}")
    public ObjectNode targetLength(@PathVariable int id) {
        if (id < nextGameID) {
            ObjectMapper mapper = new ObjectMapper();
            var result = mapper.createObjectNode();
            result.put("status", "OK");
            result.put("length", games.get(id).getTarget().length());
            return result;
        } else {
            return makeError(404, "invalid game id");
        }
    }

    @PostMapping("/check_guess/{id}")
    public ObjectNode checkGuess(@PathVariable int id, @RequestParam String guess) {
        if (id < nextGameID) {
            ObjectMapper mapper = new ObjectMapper();
            var result = mapper.createObjectNode();
            result.put("status", "OK");
            guess = guess.replaceAll("p", "+");
            System.err.println(guess);
            System.err.println(games.get(id).getTarget());
            System.err.flush();
            if (guess.length() != games.get(id).getTarget().length()) {
                return makeError(400, "guess has wrong length");
            }
            var colours = games.get(id).checkGuess(new ArrayList<>(Arrays.asList(guess.split(""))));
            var arrayNode = result.putArray("colours");
            for (var colour : colours) {
                arrayNode.add(String.valueOf(colour));
            }
            return result;
        } else {
            return makeError(404, "invalid game id");
        }
    }

    @GetMapping("has_lost/{id}")
    public ObjectNode hasLost(@PathVariable int id) {
        if (id < nextGameID) {
            ObjectMapper mapper = new ObjectMapper();
            var result = mapper.createObjectNode();
            result.put("status", "OK");
            result.put("has_lost", games.get(id).hasLost());
            return result;
        } else {
            return makeError(404, "invalid game id");
        }
    }

}
