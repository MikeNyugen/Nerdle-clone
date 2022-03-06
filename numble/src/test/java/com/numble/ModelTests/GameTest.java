package com.numble.ModelTests;

import com.numble.model.Colour;
import com.numble.model.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

  @Test
  void checkGuessTest() {
    Game game = new Game("12+6", "=18");
    ArrayList<String> userGuess = new ArrayList<String>(
        Arrays.asList("1", "1", "+", "5"));
    ArrayList<Colour> expected = new ArrayList<>(Arrays.asList(Colour.GREEN, Colour.GREY, Colour.GREEN, Colour.GREY));
    ArrayList<Colour> actual = game.checkGuess(userGuess);
    assertEquals(expected, actual);
  }

  @Test
  void anotherCheckGuessTest() {
    Game game = new Game("4321+11", "=4332");
    assertEquals("4321+11", game.getTarget());
    ArrayList<String> userGuess = new ArrayList<String>(
        Arrays.asList("1", "1", "1", "1", "+", "2", "2"));
    ArrayList<Colour> expected = new ArrayList<Colour>(
        Arrays.asList(Colour.ORANGE,Colour.ORANGE , Colour.GREY, Colour.GREEN, Colour.GREEN, Colour.ORANGE, Colour.GREY));
    ArrayList<Colour> actual = game.checkGuess(userGuess);

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

  @Test
  void hasLostTest() {
    Game game = new Game("11+1", "=13");
    assertFalse(game.hasLost());
    ArrayList<String> userGuess1 = new ArrayList<String>(
     Arrays.asList("1", "1", "+", "2"));
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
    assertDoesNotThrow(() -> new Game());
  }

  @Test
  void doesItResultInCorrectSolutionTest() {
    Game game = new Game("11+1", "13");
    ArrayList<String> userGuess1 = new ArrayList<String>(
     Arrays.asList("1", "0", "+", "2"));
    assertFalse(game.doesItResultInCorrectSolution(userGuess1));
    ArrayList<String> userGuess2 = new ArrayList<String>(
     Arrays.asList("1", "0", "+", "3"));
    assertTrue(game.doesItResultInCorrectSolution(userGuess2));
  }

  @Test
  void testHardMode() {
    Game game = new Game("11+1", "12", "HARD");
    ArrayList<String> guess = new ArrayList<String>(
            Arrays.asList("1", "1", "+", "2"));
    var ret = game.checkGuess(guess);
    for (var v : ret) {
      assertEquals(Colour.PURPLE, v);
    }
    assertFalse(game.hasWon());
    assertFalse(game.hasLost());
    guess = new ArrayList<String>(
            Arrays.asList("1", "1", "+", "1"));
    ret = game.checkGuess(guess);
    assertTrue(game.hasWon());
  }

  @Test
  void testMediumMode() {
    Game game = new Game("11+1", "12", "MEDIUM");
    ArrayList<String> guess = new ArrayList<String>(
            Arrays.asList("1", "1", "+", "2"));
    var ret = game.checkGuess(guess);
    assertFalse(game.hasWon());
    guess = new ArrayList<String>(
            Arrays.asList("1", "1", "+", "1", "=", "1", "2"));
    ret = game.checkGuess(guess);
    assertTrue(game.hasWon());
  }

  @Test
  void testSuperHardMode() {
    Game game = new Game("11+1", "12", "SUPERHARD");
    ArrayList<String> guess = new ArrayList<String>(
            Arrays.asList("1", "1", "+", "2", "=", "1", "2"));
    var ret = game.checkGuess(guess);
    for (var v : ret) {
      assertEquals(Colour.PURPLE, v);
    }
    assertFalse(game.hasWon());
    guess = new ArrayList<String>(
            Arrays.asList("1", "1", "+", "2", "=", "1", "3"));
    ret = game.checkGuess(guess);
    assertEquals(Colour.GREEN, ret.get(0));
    assertEquals(Colour.GREEN, ret.get(1));
    assertEquals(Colour.ORANGE, ret.get(3));
    assertEquals(Colour.GREY, ret.get(6));
    guess = new ArrayList<String>(
            Arrays.asList("1", "1", "+", "1", "=", "1", "2"));
    ret = game.checkGuess(guess);
    assertTrue(game.hasWon());
  }

}
