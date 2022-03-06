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
        assertEquals(0, first_game);
        var target = client.getTarget(0);
        assertFalse(client.hasWon(0));
        var colours = client.checkGuess(0, target);
        assertEquals(colours.size(), target.length());
        for (var colour : colours) {
            assertEquals(colour, Colour.GREEN);
        }
        assertTrue(client.hasWon(0));
        assertDoesNotThrow(() -> client.getTargetResult(0));
        assertThrows(RuntimeException.class, () -> client.hasWon(1));
        assertThrows(RuntimeException.class, () -> client.checkGuess(1, ""));
        assertThrows(RuntimeException.class, () -> client.getTarget(1));
        assertThrows(RuntimeException.class, () -> client.getTargetLength(1));
        assertThrows(RuntimeException.class, () -> client.getTargetResult(1));
        var second_game = client.createNewGame();
        assertEquals(1, second_game);
        assertThrows(RuntimeException.class, () -> client.checkGuess(1, ""));
        try {
            client.checkGuess(1, "");
        } catch (RuntimeException exception) {
            assertEquals(exception.getMessage(), "guess has wrong length");
        }
        target = client.getTarget(1);
        target = target.replace(target.charAt(0), target.charAt(1));
        colours = client.checkGuess(1, target);
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
