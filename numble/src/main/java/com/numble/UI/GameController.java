package com.numble.UI;

import com.numble.NumbleClient;
import com.numble.model.Colour;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Responsible for interactions with the model and the game view.
 */
public class GameController {
  private final GameView gameView;
  GridController gridController;
  private final ArrayList<JButton> calculator;
  private final ArrayList<JLabel> values;
  private final Hashtable<String, Hashtable<Colour, Color>> colourMapping;
  private final StringBuilder userGuess;
  String[] userGuessArr;
  ArrayList<String> userGuessList;
  ArrayList<Colour> colourCode;
  NumbleClient client;
  int gameID;

  private int gridIndex = 0;
  private int row = 0;
  private final int columns;
  private final int targetLength;

  public GameController(GameView gameView, int gameID, NumbleClient client, String mode) throws URISyntaxException,
          IOException, ParseException, InterruptedException {
    this.gameView = gameView;
    this.gridController = gameView.getGridView();
    this.client = client;
    this.gameID = gameID;
    calculator = gameView.getCalculator();
    values = new ArrayList<>();
    colourMapping = new Hashtable<>();
    userGuess = new StringBuilder();
    columns = gridController.getColumns();
    targetLength = client.getTargetLength(gameID);
    System.out.println(mode);
    if (mode.equals("EASY") || mode.equals("HARD")) {
      drawResult();
    }
    addListeners();
  }

  private void addListeners() {
    addButtonListener(gameView.getNumber0(), "0");
    addButtonListener(gameView.getNumber1(), "1");
    addButtonListener(gameView.getNumber2(), "2");
    addButtonListener(gameView.getNumber3(), "3");
    addButtonListener(gameView.getNumber4(), "4");
    addButtonListener(gameView.getNumber5(), "5");
    addButtonListener(gameView.getNumber6(), "6");
    addButtonListener(gameView.getNumber7(), "7");
    addButtonListener(gameView.getNumber8(), "8");
    addButtonListener(gameView.getNumber9(), "9");
    addButtonListener(gameView.getPlus(), "+");
    addButtonListener(gameView.getMinus(), "-");
    addButtonListener(gameView.getMultiply(), "x");
    addButtonListener(gameView.getDivide(), "/");
    addButtonListener(gameView.getEquals(), "=");
    addDeleteListener();
    addSubmitListener();
  }

  /**
   * Draws a value on the grid when a button is clicked.
   *
   * @param button the button that the listener is added to
   * @param value  the value that is drawn on the grid
   */
  private void addButtonListener(JButton button, String value) {
    button.addActionListener(e -> {
      int xPadding = 32;
      int yPadding = 15;
      if (gridIndex != targetLength) {
        gridController.getGrid().get(row).get(gridIndex).setValue(value);
        userGuess.append(gridController.getGrid().get(row).get(gridIndex).getValue());
        int x = gridController.getGrid().get(row).get(gridIndex).getxPosition() + xPadding;
        int y = gridController.getGrid().get(row).get(gridIndex).getyPosition() + yPadding;
        drawValue(x, y, value);
        gridIndex++;
      }
    });
  }

  /**
   * Draws a value on the grid.
   *
   * @param x     the x position of the value drawn
   * @param y     the y position of the value drawn
   * @param value the value that is to be drawn
   */
  private void drawValue(int x, int y, String value) {
    JLabel label = new JLabel(value);
    label.setBounds(x, y, 50, 50);
    label.setFont(new Font("Arial", Font.BOLD, 30));
    label.setForeground(Color.BLACK);
    values.add(label);
    gameView.layeredPane.add(label, 1);
    gameView.mainFrame.repaint();
  }

  /**
   * Draw the result of the equation to the user on easy and hard mode.
   *
   * @throws URISyntaxException
   * @throws IOException
   * @throws ParseException
   * @throws InterruptedException
   */
  public void drawResult() throws URISyntaxException, IOException, ParseException, InterruptedException {
    String targetResult = client.getTargetResult(gameID);
    String[] targetResultArr = targetResult.split("");
    ArrayList<String> targetList = new ArrayList<>(Arrays.asList(targetResultArr));
    for (int i = 0; i < 5; i++) {
      for (int j = 1; j < targetList.size() + 1; j++) {
        int xPosition = gridController.getGrid().get(i).get(columns - j).getxPosition() + 35;
        int yPosition = gridController.getGrid().get(i).get(columns - j).getyPosition() + 15;
        String value = targetList.get(targetList.size() - j);
        JLabel label = new JLabel(value);
        label.setBounds(xPosition, yPosition, 50, 50);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(Color.BLACK);
        gameView.layeredPane.add(label, 1);
        gameView.mainFrame.repaint();
      }
    }
  }

  /**
   * Deletes numbers on the grid when the 'del' button is clicked.
   */
  public void addDeleteListener() {
    gameView.getDelete().addActionListener(e -> {
      if (gridIndex > 0 && userGuess.length() > 0) {
        JLabel label = values.get(gridIndex - 1);
        values.remove(label);
        label.setVisible(false);
        userGuess.setLength(userGuess.length() - 1);
        gameView.layeredPane.remove(label);
        gameView.mainFrame.repaint();
        gridIndex--;
      }
    });
  }

  /**
   * Submits the user's guess.
   * Colours the grid and keyboard and checks if the user
   * has won or lost.
   */
  public void addSubmitListener() {
    gameView.getSubmit().addActionListener(e -> {
      try {
        if (userGuess.length() == targetLength) {
          userGuessArr = userGuess.toString().split("");
          userGuessList = new ArrayList<>(Arrays.asList(userGuessArr));
          try {
            colourCode = (ArrayList<Colour>) client.checkGuess(gameID, userGuess.toString());
          } catch (URISyntaxException | IOException | InterruptedException | ParseException ex) {
            ex.printStackTrace();
          }
          colourGrid(colourCode, userGuessList);
          colourKeyboard(colourCode, userGuessList);
          reset();
          if (client.hasWon(gameID)) {
            winGame();
          }
          if (client.hasLost(gameID)) {
            loseGame();
          }
        }
      } catch (URISyntaxException | IOException | InterruptedException | ParseException ex) {
        ex.printStackTrace();
      }
    });
  }

  /**
   * Colours the grid.
   * @param colours  list containing the colours of each cell in the grid
   * @param userGuessList  list containing the user's guess
   */
  private void colourGrid(ArrayList<Colour> colours, ArrayList<String> userGuessList) {
    for (int i = 0; i < colours.size(); i++) {
      gridController.getGrid().get(row).get(i).setColour(colours.get(i));
      Color color;
      Hashtable<Colour, Color> temp = new Hashtable<>();
      if (colours.get(i) == Colour.GREEN) {
        color = new Color(0, 163, 108);
        temp.put(colours.get(i), color);
      } else if (colours.get(i) == Colour.GREY) {
        color = Color.lightGray;
        temp.put(colours.get(i), color);
      } else if (colours.get(i) == Colour.ORANGE) {
        color = new Color(251, 177, 23);
        temp.put(colours.get(i), color);
      } else if (colours.get(i) == Colour.PURPLE) {
        temp.put(colours.get(i), Color.white);
      }
      colourMapping.put(userGuessList.get(i), temp);
    }
  }

  /**
   * Colours the keyboard.
   * @param colours  list containing the colours of each cell in the grid.
   * @param userGuessList  list containing the user's guess
   */
  private void colourKeyboard(ArrayList<Colour> colours, ArrayList<String> userGuessList) {
    for (JButton jButton : calculator) {
      for (int j = 0; j < userGuessList.size(); j++) {
        if (userGuessList.get(j).equals(jButton.getText())) {
          Hashtable<Colour, Color> temp = colourMapping.get(userGuessList.get(j));
          gameView.setColor(jButton, temp.get(colours.get(j)));
        }
      }
    }
  }

  /**
   * Resets variables and moves onto the next row.
   */
  private void reset() {
    row++;
    gridIndex = 0;
    values.clear();
    userGuess.setLength(0);
    gameView.mainFrame.repaint();
  }

  /**
   * Put the game in the win state.
   */
  private void winGame() {
    JOptionPane.showMessageDialog(gameView.mainFrame, "Well done! You've guessed the equation :)");
    gameView.mainFrame.repaint();
    for (JButton button : calculator) {
      button.setEnabled(false);
    }
  }

  /**
   * Put the game in the lost state.
   */
  private void loseGame() {
    JOptionPane.showMessageDialog(gameView.mainFrame, "You have lost the game :(");
    gameView.mainFrame.repaint();
    for (JButton button : calculator) {
      button.setEnabled(false);
    }
  }
}

