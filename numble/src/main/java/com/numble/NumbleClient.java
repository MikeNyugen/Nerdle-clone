package com.numble;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;

import com.numble.model.Colour;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class NumbleClient {
    private final String endpoint;
    HttpClient httpclient = HttpClient.newBuilder().build();

    NumbleClient(String endpoint) {
        this.endpoint = endpoint;
    }

    NumbleClient() {
        this("http://localhost:8080/");
    }

    public Integer createNewGame() throws URISyntaxException, IOException, InterruptedException, org.json.simple.parser.ParseException {
        HttpRequest request = HttpRequest.newBuilder(new URI(endpoint + "new_game")).POST(HttpRequest.BodyPublishers.noBody()).build();
        var response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        System.err.println(response.body());
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.body());
        return Integer.valueOf(json.get("game_id").toString());
    }

    public String getTarget(Integer game_id)
            throws URISyntaxException, IOException, InterruptedException, org.json.simple.parser.ParseException {
        HttpRequest request = HttpRequest.newBuilder(new URI(endpoint + "get_target/" + game_id.toString())).
                GET().build();
        var response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        System.err.println(response.body());
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.body());
        if (json.get("status").toString().equals("OK")) {
            return json.get("target").toString();
        } else {
            throw new RuntimeException(((JSONObject)json.get("error")).get("description").toString());
        }
    }

    public String getTargetResult(Integer game_id)
            throws URISyntaxException, IOException, InterruptedException, org.json.simple.parser.ParseException {
        HttpRequest request = HttpRequest.newBuilder(new URI(endpoint + "get_target_result/" + game_id.toString())).
                GET().build();
        var response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        System.err.println(response.body());
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.body());
        if (json.get("status").toString().equals("OK")) {
            return json.get("target_result").toString();
        } else {
            throw new RuntimeException(((JSONObject)json.get("error")).get("description").toString());
        }
    }

    public boolean hasWon(Integer game_id)
            throws URISyntaxException, IOException, InterruptedException, org.json.simple.parser.ParseException {
        HttpRequest request = HttpRequest.newBuilder(new URI(endpoint + "has_won/" + game_id.toString())).
                GET().build();
        var response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        System.err.println(response.body());
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.body());
        if (json.get("status").toString().equals("OK")) {
            return json.get("has_won").toString().equals("true");
        } else {
            throw new RuntimeException(((JSONObject)json.get("error")).get("description").toString());
        }
    }

    public Integer getTargetLength(Integer game_id)
            throws URISyntaxException, IOException, InterruptedException, org.json.simple.parser.ParseException {
        HttpRequest request = HttpRequest.newBuilder(new URI(endpoint + "target_length/" + game_id.toString())).
                GET().build();
        var response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        System.err.println(response.body());
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.body());
        if (json.get("status").toString().equals("OK")) {
            return Integer.valueOf(json.get("length").toString());
        } else {
            throw new RuntimeException(((JSONObject)json.get("error")).get("description").toString());
        }
    }

    public List<Colour> checkGuess(Integer game_id, String guess)
            throws URISyntaxException, IOException, InterruptedException, org.json.simple.parser.ParseException {
        var uri = new URI(endpoint + "check_guess/" + game_id.toString()) + "?guess=" + guess.replace('+', 'p');
        HttpRequest request = HttpRequest.newBuilder(URI.create(uri)).POST(HttpRequest.BodyPublishers.noBody()).build();
        var response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        System.err.println(response.body());
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.body());
        if (json.get("status").toString().equals("OK")) {
            JSONArray colours = (JSONArray) json.get("colours");
            var result = new ArrayList<Colour>();
            for (Object colour : colours) {
                result.add(Colour.valueOf(colour.toString()));
            }
            return result;
        } else {
            throw new RuntimeException(((JSONObject)json.get("error")).get("description").toString());
        }
    }

    public boolean hasLost(Integer game_id) throws URISyntaxException, IOException, InterruptedException, ParseException {
        HttpRequest request = HttpRequest.newBuilder(new URI(endpoint + "has_lost/" + game_id.toString())).
                GET().build();
        var response = httpclient.send(request, HttpResponse.BodyHandlers.ofString());
        System.err.println(response.body());
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(response.body());
        if (json.get("status").toString().equals("OK")) {
            return json.get("has_lost").toString().equals("true");
        } else {
            throw new RuntimeException(((JSONObject)json.get("error")).get("description").toString());
        }
    }
}
