package com.numble.ModelTests;

import com.numble.model.Equation;
import com.numble.model.Game;
import org.junit.jupiter.api.Test;

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
    Game game = mock(Game.class);
    when(game.getTarget()).thenReturn("12+6=18");
    assertEquals("[0,3,0,3,0,0,0]", game.checkGuess("11+5=18").toString());
    assertEquals(game.checkGuess("[2,2,0,3,0,3]"), game.checkGuess("81+9=90").toString());
  }

  @Test
  void equationGeneratorTest() {
    Game game = mock(Game.class);
    assertNotNull(game.generateEquation());
  }

  @Test
  void hasWonTest() {
    Game game = mock(Game.class);
    
    when(game.getTarget()).thenReturn("11+1=13");
    assertFalse(game.hasWon());

    game.checkGuess("11+1=14");
    assertFalse(game.hasWon());

    game.checkGuess("11+1=13");
    assertTrue(game.hasWon());
  }

}
