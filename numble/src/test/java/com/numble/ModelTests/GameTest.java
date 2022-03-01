package com.numble.ModelTests;

import com.numble.model.Equation;
import com.numble.model.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTest {

  @Test
  void checkGuessTest() {
    Game game = new Game("12+6", "=18");
    ArrayList<String> userGuess = new ArrayList<String>(
        Arrays.asList("1", "1", "+", "5"));
    ArrayList<String> expected = new ArrayList<>(Arrays.asList("GREEN", "GREY", "GREEN", "GREY"));
    ArrayList<String> actual = game.checkGuess(userGuess);
    assertEquals(expected, actual);
  }

  @Test
  void anotherCheckGuessTest() {
    Game game = new Game("4321+11", "=4332");
    assertEquals("4321+11", game.getTarget());
    ArrayList<String> userGuess = new ArrayList<String>(
        Arrays.asList("1", "1", "1", "1", "+", "2", "2"));
    ArrayList<String> expected = new ArrayList<>(
        Arrays.asList("ORANGE", "ORANGE", "GREY", "GREEN", "GREEN", "ORANGE", "GREY"));
    ArrayList<String> actual = game.checkGuess(userGuess);

    assertEquals(expected, actual);
  }

  @Test
  void hasWonTest() {
    Game game = new Game("11+1", "=13");
    assertFalse(game.hasWon());

    ArrayList<String> userGuess1 = new ArrayList<String>(
        Arrays.asList("1", "1", "+", "2"));
    game.checkGuess(userGuess1);
    assertFalse(game.hasWon());

    ArrayList<String> userGuess2 = new ArrayList<String>(
        Arrays.asList("1", "1", "+", "1"));
    game.checkGuess(userGuess2);
    assertTrue(game.hasWon());
  }

}
