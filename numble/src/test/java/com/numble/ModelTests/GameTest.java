package com.numble.ModelTests;

import com.numble.model.Colour;
import com.numble.model.Game;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

  @Test
  void checkGuessTest() {
    Game game = new Game("12+6", "=18");
    List<String> userGuess = Arrays.asList("1", "1", "+", "5");
    List<Colour> expected = Arrays.asList(Colour.GREEN, Colour.GREY, Colour.GREEN, Colour.GREY);
    List<Colour> actual = game.checkGuess(userGuess);
    assertEquals(expected, actual);
  }

  @Test
  void anotherCheckGuessTest() {
    Game game = new Game("4321+11", "=4332");
    assertEquals("4321+11", game.getTarget());
    List<String> userGuess = Arrays.asList("1", "1", "1", "1", "+", "2", "2");
    List<Colour> expected = Arrays.asList(Colour.ORANGE,Colour.ORANGE , Colour.GREY, Colour.GREEN, Colour.GREEN, Colour.ORANGE, Colour.GREY);
    List<Colour> actual = game.checkGuess(userGuess);

    assertEquals(expected, actual);
  }

  @Test
  void hasWonTest() {
    Game game = new Game("11+1", "=13");
    assertFalse(game.hasWon());

    List<String> userGuess1 = Arrays.asList("1", "1", "+", "2");
    game.checkGuess(userGuess1);
    assertFalse(game.hasWon());

    List<String> userGuess2 = Arrays.asList("1", "1", "+", "1");
    game.checkGuess(userGuess2);
    assertTrue(game.hasWon());
  }

  @Test
  void hasLostTest() {
    Game game = new Game("11+1", "=13");
    assertFalse(game.hasLost());
    List<String> userGuess1 = Arrays.asList("1", "1", "+", "2");
    game.checkGuess(userGuess1); //guess no.1 is wrong
    assertFalse(game.hasLost());
    game.checkGuess(userGuess1); //guess no.2 is wrong
    assertFalse(game.hasLost());
    game.checkGuess(userGuess1); //guess no.3 is wrong
    assertFalse(game.hasLost());
    game.checkGuess(userGuess1); //guess no.4 is wrong
    assertFalse(game.hasLost());
    game.checkGuess(userGuess1); //guess no.5 is wrong and ran out of guesses
    assertTrue(game.hasLost());
  }

  @Test
  void equationTest() {
    assertDoesNotThrow(() -> new Game("EASY"));
  }


  @Test
  void testHardMode() {
    Game game = new Game("11+1", "=12", "HARD");
    List<String> guess = Arrays.asList("1", "1", "+", "2");
    var ret = game.checkGuess(guess);
    for (var v : ret) {
      assertEquals(Colour.PURPLE, v);
    }
    assertFalse(game.hasWon());
    assertFalse(game.hasLost());
    guess = Arrays.asList("1", "1", "+", "1");
    ret = game.checkGuess(guess);
    assertTrue(game.hasWon());
  }

  @Test
  void testMediumMode() {
    Game game = new Game("11+1", "=12", "MEDIUM");
    List<String> guess = Arrays.asList("1", "1", "+", "2");
    var ret = game.checkGuess(guess);
    assertFalse(game.hasWon());
    guess = Arrays.asList("1", "1", "+", "1", "=", "1", "2");
    ret = game.checkGuess(guess);
    assertTrue(game.hasWon());
  }

  @Test
  void testSuperHardMode() {
    Game game = new Game("11+1", "=12", "SUPERHARD");
    List<String> guess = Arrays.asList("1", "1", "+", "2", "=", "1", "2");
    var ret = game.checkGuess(guess);
    for (var v : ret) {
      assertEquals(Colour.PURPLE, v);
    }
    assertFalse(game.hasWon());
    guess = Arrays.asList("1", "1", "+", "2", "=", "1", "3");
    ret = game.checkGuess(guess);
    assertEquals(Colour.GREEN, ret.get(0));
    assertEquals(Colour.GREEN, ret.get(1));
    assertEquals(Colour.ORANGE, ret.get(3));
    assertEquals(Colour.GREY, ret.get(6));
    guess = Arrays.asList("1", "1", "+", "1", "=", "1", "2");
    ret = game.checkGuess(guess);
    assertTrue(game.hasWon());
  }

  @Test
  void testModes() {
    for (var mode : new String[] {"EASY", "MEDIUM", "HARD", "SUPERHARD"}) {
      var game = new Game(mode);
      game.checkGuess(List.of(game.getTarget().split("")));
    }
  }

}
