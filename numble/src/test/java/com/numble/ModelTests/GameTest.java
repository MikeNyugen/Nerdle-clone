package com.numble.ModelTests;

import com.numble.model.Colour;
import com.numble.model.Equation;
import com.numble.model.Game;
import com.fathzer.soft.javaluator.DoubleEvaluator;
import org.apache.el.lang.ExpressionBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.beans.Expression;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTest {

  @Test
  void checkGuessTest() {
    Game game = new Game("12+6", "=18");
    game.setGameMode("EASY");
    ArrayList<String> userGuess = new ArrayList<String>(
        Arrays.asList("1", "1", "+", "5"));
    ArrayList<Colour> expected = new ArrayList<>(Arrays.asList(Colour.GREEN, Colour.GREY, Colour.GREEN, Colour.GREY));
    ArrayList<Colour> actual = game.checkGuess(userGuess);
    assertEquals(expected, actual);
  }

  @Test
  void anotherCheckGuessTest() {
    Game game = new Game("4321+11", "=4332");
    game.setGameMode("EASY");
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
    game.setGameMode("EASY");
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
    game.setGameMode("EASY");
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

}
