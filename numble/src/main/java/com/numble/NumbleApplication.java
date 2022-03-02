package com.numble;

import com.numble.UI.GameView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NumbleApplication {

	public static void main(String[] args) {
		GameView gameView = new GameView();
		SpringApplication.run(NumbleApplication.class, args);
	}
}
