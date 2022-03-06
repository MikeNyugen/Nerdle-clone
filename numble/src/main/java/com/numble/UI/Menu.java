package com.numble.UI;

import com.numble.NumbleClient;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * The start menu for the game.
 */
public class Menu {

  private final static int FRAME_WIDTH = 500;
  private final static int FRAME_HEIGHT = 400;

  JFrame menuFrame;

  public Menu() {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    setupFrame();

    displayTitle();
    easyButton();
    hardButton();
    option1();
    option2();
    option3();

    menuFrame.paintAll(menuFrame.getGraphics());
  }

  private void setupFrame() {
    menuFrame = new JFrame("Numble");
    menuFrame.setLayout(null);
    menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    menuFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - menuFrame.getWidth()) / 2);
    int y = (int) ((dimension.getHeight() - menuFrame.getHeight()) / 2);
    menuFrame.setLocation(x, y);
    menuFrame.setVisible(true);
  }

  private void displayTitle() {
    JLabel title = new JLabel("Numble");
    title.setBounds(140, 15, 300, 100);
    title.setFont(new Font("Comic Sans MS", Font.BOLD, 65));
    title.setForeground(Color.ORANGE);
    menuFrame.add(title);
  }

  private void easyButton() {
    JButton easyMode = new JButton("Easy Mode");
    easyMode.setBounds(180, 125, 150, 50);
    setUpButton(easyMode, new Color(34, 139, 34));
  }

  private void hardButton() {
    JButton hardMode = new JButton("Hard Mode");
    hardMode.setBounds(180, 190, 150, 50);
    setUpButton(hardMode, new Color(139, 0, 0));
  }

  private void option1() {
    JCheckBox option1 = new JCheckBox("Guess must add to correct solution");
    setUpCheckbox(option1, 0);
  }

  private void option2() {
    JCheckBox option2 = new JCheckBox("Correct guesses must be reused");
    setUpCheckbox(option2, 30);
  }

  private void option3() {
    JCheckBox option3 = new JCheckBox("No hint");
    setUpCheckbox(option3, 60);
  }

  private void setUpButton(JButton button, Color color) {
    button.setBackground(color);
    button.setForeground(Color.white);
    button.setFocusPainted(false);
    button.setBorder(new LineBorder(color));
    button.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
    // Switch JPanel when button is clicked
    button.addActionListener(e -> {
      menuFrame.setVisible(false);
      // Server initialisation should occur here
      NumbleClient client = new NumbleClient();
      int gameID = 0;
      try {
        gameID = client.createNewGame();
      } catch (URISyntaxException | IOException | ParseException | InterruptedException ex) {
        ex.printStackTrace();
      }
      GameView gameView = null;
      try {
        gameView = new GameView(gameID, client);
      } catch (URISyntaxException | IOException | ParseException | InterruptedException ex) {
        ex.printStackTrace();
      }
      try {
        GameController controller = new GameController(gameView, gameID, client);
      } catch (URISyntaxException | IOException | ParseException | InterruptedException ex) {
        ex.printStackTrace();
      }
    });
    menuFrame.add(button);
  }

  private void setUpCheckbox(JCheckBox checkbox, int space) {
    checkbox.setBounds(145, 260 + space, 300, 20);
    checkbox.setFont(new Font("Arial", Font.PLAIN, 14));
    checkbox.setFocusPainted(false);
    menuFrame.add(checkbox);
  }
}
