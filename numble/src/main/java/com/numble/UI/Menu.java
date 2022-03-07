package com.numble.UI;

import com.numble.NumbleClient;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Hashtable;

/**
 * The start menu for the game.
 */
public class Menu {

  private final static int FRAME_WIDTH = 500;
  private final static int FRAME_HEIGHT = 450;
  private final Hashtable<String, String> modeMap = new Hashtable<>();

  JFrame menuFrame;

  public Menu() {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    setupFrame();
    displayTitle();
    setupModeMap();
    easyButton();
    mediumButton();
    hardButton();
    superHardButton();
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
    title.setForeground(new Color(255,215,0));
    menuFrame.add(title);
  }

  private void setupModeMap() {
    modeMap.put("Easy Mode", "EASY");
    modeMap.put("Medium Mode", "MEDIUM");
    modeMap.put("Hard Mode", "HARD");
    modeMap.put("Super Hard mode", "SUPERHARD");
  }

  private void easyButton() {
    JButton easyMode = new JButton("Easy Mode");
    easyMode.setBounds(180, 125, 150, 50);
    setUpButton(easyMode, new Color(34, 139, 34));
  }

  private void mediumButton() {
    JButton mediumMode = new JButton("Medium Mode");
    mediumMode.setBounds(180, 190, 150, 50);
    setUpButton(mediumMode, Color.ORANGE);
  }

  private void hardButton() {
    JButton hardMode = new JButton("Hard Mode");
    hardMode.setBounds(180, 255, 150, 50);
    setUpButton(hardMode, Color.RED);
  }

  private void superHardButton() {
    JButton superHardMode = new JButton("Super Hard Mode");
    superHardMode.setBounds(180,320,150,50);
    setUpButton(superHardMode, new Color(139,0,0));
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
      NumbleClient client = new NumbleClient();
      int gameID = 0;
      String mode = modeMap.get(button.getText());
      try {
        gameID = client.createNewGame(mode);
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
        assert gameView != null;
        GameController controller = new GameController(gameView, gameID, client);
      } catch (URISyntaxException | IOException | ParseException | InterruptedException ex) {
        ex.printStackTrace();
      }
    });
    menuFrame.add(button);
  }

}
