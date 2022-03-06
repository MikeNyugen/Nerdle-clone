package com.numble;

import com.numble.model.Colour;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServerInteractionTests {
    static Thread server;
    static NumbleClient client = new NumbleClient();

    @BeforeAll
    static void before() throws InterruptedException {
        server = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] args = new String[0];
                NumbleApplication.main(args);
            }
        });
        server.start();
        Thread.sleep(2500);
    }

    @Test
    public void firstTest() throws URISyntaxException, IOException, ParseException, InterruptedException {
        var first_game = client.createNewGame();
        var target = client.getTarget(first_game);
        assertFalse(client.hasWon(first_game));
        var colours = client.checkGuess(first_game, target);
        assertEquals(colours.size(), target.length());
        for (var colour : colours) {
            assertEquals(colour, Colour.GREEN);
        }
        assertTrue(client.hasWon(first_game));
        assertDoesNotThrow(() -> client.getTargetResult(first_game));
        assertThrows(RuntimeException.class, () -> client.hasWon(first_game + 1));
        assertThrows(RuntimeException.class, () -> client.checkGuess(first_game + 1, ""));
        assertThrows(RuntimeException.class, () -> client.getTarget(first_game + 1));
        assertThrows(RuntimeException.class, () -> client.getTargetLength(first_game + 1));
        assertThrows(RuntimeException.class, () -> client.getTargetResult(first_game + 1));
        var second_game = client.createNewGame();
        assertThrows(RuntimeException.class, () -> client.checkGuess(second_game, ""));
        try {
            client.checkGuess(second_game, "");
        } catch (RuntimeException exception) {
            assertEquals(exception.getMessage(), "guess has wrong length");
        }
        target = client.getTarget(second_game);
        target = target.replace(target.charAt(0), target.charAt(1));
        colours = client.checkGuess(second_game, target);
        assertNotEquals(colours.get(0), 0);
    }

    @Test
    public void simultaneousUsers() throws URISyntaxException, IOException, ParseException, InterruptedException {
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
        for (var mode : new String[] {"EASY", "MEDIUM", "HARD", "SUPERHARD"}) {
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
