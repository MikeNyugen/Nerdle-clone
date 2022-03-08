package com.numble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Tests the API.
 */
public class APITests {
  WebTestClient client;

  String[] getMethods = new String[]{"get_target_result", "get_target", "has_won",
          "target_length", "has_lost", "game_mode"};

  @BeforeEach
  public void before() {
    client = WebTestClient.bindToController(new NumbleAPI()).build();
  }

  @Test
  public void createGame() {
    client.post().uri("/new_game?mode=EASY")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody().jsonPath("status").isEqualTo("OK")
            .jsonPath("game_id").isEqualTo(0);
  }

  @Test
  public void notFoundError() {
    for (var method : getMethods) {
      client.get().uri("/" + method + "/0")
              .accept(MediaType.APPLICATION_JSON)
              .exchange()
              .expectStatus().isNotFound();
    }
    client.post().uri("/" + "check_guess" + "/0?guess=10p2")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();

  }

  @Test
  public void badLengthError() {
    client.post().uri("/new_game?mode=EASY")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody().jsonPath("status").isEqualTo("OK")
            .jsonPath("game_id").isEqualTo(0);
    client.post().uri("/" + "check_guess" + "/0?guess=1")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest();
  }

  @Test
  public void checkMethods() {
    client.post().uri("/new_game?mode=EASY")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody().jsonPath("status").isEqualTo("OK")
            .jsonPath("game_id").isEqualTo(0);
    for (var method : getMethods) {
      client.get().uri("/" + method + "/0")
              .accept(MediaType.APPLICATION_JSON)
              .exchange()
              .expectStatus().isOk();
    }
  }

}
