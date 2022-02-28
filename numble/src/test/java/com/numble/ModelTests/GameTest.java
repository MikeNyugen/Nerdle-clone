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
  void gameCreationTest() {
    Game game = new Game();
    assertNotNull(game);
  }

  @Test
  void checkGuessTest() {
    Game game = new Game();
    game.target = "12+6=18";
    assertEquals("12+6=18", game.getTarget());
    ArrayList<String> userGuess = new ArrayList<String>(
     Arrays.asList("1", "1", "+", "5", "=", "1", "8")
    );
    assertEquals("[0, 2, 0, 2, 0, 0, 0]", game.checkGuess(userGuess).toString());
  }

  @Test
  void anotherCheckGuessTest() {
    Game game = new Game();
    game.target = "4321+11";
    assertEquals("4321+11", game.getTarget());
    ArrayList<String> userGuess = new ArrayList<String>(
     Arrays.asList("1", "1", "1", "1", "+", "2", "2")
    );
    assertEquals("[1, 1, 2, 0, 0, 1, 2]", game.checkGuess(userGuess).toString());
  }

  @Test
  void equationGeneratorTest() {
    Game game = new Game();
    assertNotNull(game.generateEquation());
  }

  @Test
  void hasWonTest() {
    Game game = new Game();
    game.target = "11+1=13";
    assertFalse(game.hasWon());

    ArrayList<String> userGuess1 = new ArrayList<String>(
     Arrays.asList("1", "1", "+", "1", "=", "1", "4")
    );
    game.checkGuess(userGuess1);
    assertFalse(game.hasWon());

    ArrayList<String> userGuess2 = new ArrayList<String>(
     Arrays.asList("1", "1", "+", "1", "=", "1", "3")
    );
    game.checkGuess(userGuess2);
    assertTrue(game.hasWon());

  }


}
