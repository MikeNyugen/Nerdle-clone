package com.numble;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

public class APITests {
    WebTestClient client;

    @BeforeEach
    public void before() {
        client = WebTestClient.bindToController(new NumbleAPI()).build();
    }

    @Test
    public void createGame() {
        client.post().uri("/new_game")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("status").isEqualTo("OK")
                .jsonPath("game_id").isEqualTo(0);
    }

    @Test
    public void notFoundError() {
        client.get().uri("/get_target_result/0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }
}
