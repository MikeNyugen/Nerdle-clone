package com.numble;

import com.numble.model.Colour;
import java.io.IOException;
import java.net.URISyntaxException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests the interactions between the server and the client.
 */
public class ClientServerInteractionTests {
  static Thread server;
  static NumbleClient client = new NumbleClient();

  @BeforeAll
  static void before() throws InterruptedException {
    server = new Thread(() -> {
      String[] args = new String[0];
      SpringApplication.run(NumbleApplication.class, args);
    });
    server.start();
    Thread.sleep(2500);
  }

  @Test
  public void firstTest() throws URISyntaxException, IOException,
          ParseException, InterruptedException {
    var firstGame = client.createNewGame();
    var target = client.getTarget(firstGame);
    assertFalse(client.hasWon(firstGame));
    var colours = client.checkGuess(firstGame, target);
    assertEquals(colours.size(), target.length());
    for (var colour : colours) {
      assertEquals(colour, Colour.GREEN);
    }
    assertTrue(client.hasWon(firstGame));
    assertDoesNotThrow(() -> client.getTargetResult(firstGame));
    assertThrows(RuntimeException.class, () -> client.hasWon(firstGame + 1));
    assertThrows(RuntimeException.class, () -> client.checkGuess(firstGame + 1, ""));
    assertThrows(RuntimeException.class, () -> client.getTarget(firstGame + 1));
    assertThrows(RuntimeException.class, () -> client.getTargetLength(firstGame + 1));
    assertThrows(RuntimeException.class, () -> client.getTargetResult(firstGame + 1));
    var secondGame = client.createNewGame();
    assertThrows(RuntimeException.class, () -> client.checkGuess(secondGame, ""));
    try {
      client.checkGuess(secondGame, "");
    } catch (RuntimeException exception) {
      assertEquals(exception.getMessage(), "guess has wrong length");
    }
    target = client.getTarget(secondGame);
    target = target.replace(target.charAt(0), target.charAt(1));
    colours = client.checkGuess(secondGame, target);
    assertNotEquals(colours.get(0), 0);
  }

  @Test
  public void simultaneousUsers() throws URISyntaxException, IOException,
          ParseException, InterruptedException {
    var firstUser = client.createNewGame();
    var secondUser = client.createNewGame();
    var thirdUser = client.createNewGame();
    var target1 = client.getTarget(firstUser);
    var target2 = client.getTarget(secondUser);
    var target3 = client.getTarget(thirdUser);
    client.checkGuess(secondUser, target2);
    client.checkGuess(thirdUser, target3);
    client.checkGuess(firstUser, target1);
    assertTrue(client.hasWon(thirdUser));
    assertTrue(client.hasWon(secondUser));
    assertTrue(client.hasWon(firstUser));
    assertFalse(client.hasLost(firstUser));
    assertFalse(client.hasLost(secondUser));
    assertFalse(client.hasLost(thirdUser));
  }

  @Test
  void testModes() throws URISyntaxException, IOException, ParseException, InterruptedException {
    for (var mode : new String[]{"EASY", "MEDIUM", "HARD", "SUPERHARD"}) {
      var game = client.createNewGame(mode);
      var target = client.getTarget(game);
      client.checkGuess(game, target);
      assertTrue(client.hasWon(game));
    }
  }

  @AfterAll
  static void after() {
    server.stop();
  }
}
