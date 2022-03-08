package com.numble.UI;

import com.numble.NumbleClient;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Responsible for the view of the game.
 */
public class GameView extends JPanel {
  private final static int FRAME_WIDTH = 1000;
  private final static int FRAME_HEIGHT = 1000;
  private final static int BUTTON_WIDTH = 80;
  private final static int BUTTON_HEIGHT = 80;
  private final static int SPACE = 85;

  JFrame mainFrame;
  JButton number0;
  JButton number1;
  JButton number2;
  JButton number3;
  JButton number4;
  JButton number5;
  JButton number6;
  JButton number7;
  JButton number8;
  JButton number9;
  JButton plus;
  JButton minus;
  JButton multiply;
  JButton divide;
  JButton delete;
  JButton equals;
  JButton submit;
  String mode;

  ArrayList<JButton> calculator = new ArrayList<>();
  JLayeredPane layeredPane;
  GridController gameGrid;

  public GameView(int gameID, NumbleClient client, String mode) throws URISyntaxException, IOException, ParseException, InterruptedException {
    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.mode = mode;
    setupFrame();
    initializeButtons();
    gameGrid = new GridController(gameID, client, mode);
    layeredPane.add(gameGrid, 0);
    mainFrame.add(gameGrid);
    mainFrame.paintAll(mainFrame.getGraphics());
    displayRules();
  }

  /**
   * Displays the rules of the mode chosen via a pop-up.
   */
  private void displayRules() {
    switch (mode) {
      case "EASY":
        JOptionPane.showMessageDialog(mainFrame, "There are no constraints.");
        break;
      case "MEDIUM":
        JOptionPane.showMessageDialog(mainFrame, "The resulting equation will not be displayed.");
        break;
      case "HARD":
        String message = "Your guess must be equal to the target in order to be valid.\n" +
                "Invalid guesses will show up in purple!";
        JOptionPane.showMessageDialog(mainFrame, message);
        break;
      case "SUPERHARD":
        String message2 = "Your guess must be equal to the target in order to be valid.\n" +
                "The resulting equation will not be displayed\n" +
                "Invalid guesses will show up in purple!";
        JOptionPane.showMessageDialog(mainFrame, message2);
        break;
    }
  }

  /**
   * Initialises the buttons and adds it to the view.
   */
  private void initializeButtons() {
    int xPosition = 287;
    int yPosition = 540;

    number0 = new JButton("0");
    number0.setBounds(xPosition, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(number0);

    number1 = new JButton("1");
    number1.setBounds(xPosition + SPACE, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(number1);

    number2 = new JButton("2");
    number2.setBounds(xPosition + SPACE * 2, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(number2);

    plus = new JButton("+");
    plus.setBounds(xPosition + SPACE * 3, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(plus);

    multiply = new JButton("x");
    multiply.setBounds(xPosition + SPACE * 4, yPosition, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(multiply);

    number3 = new JButton("3");
    number3.setBounds(xPosition, yPosition + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(number3);

    number4 = new JButton("4");
    number4.setBounds(xPosition + SPACE, yPosition + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(number4);

    number5 = new JButton("5");
    number5.setBounds(xPosition + SPACE * 2, yPosition + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(number5);

    minus = new JButton("-");
    minus.setBounds(xPosition + SPACE * 3, yPosition + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(minus);

    divide = new JButton("/");
    divide.setBounds(xPosition + SPACE * 4, yPosition + SPACE, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(divide);

    number6 = new JButton("6");
    number6.setBounds(xPosition, yPosition + SPACE * 2, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(number6);

    number7 = new JButton("7");
    number7.setBounds(xPosition + SPACE, yPosition + SPACE * 2, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(number7);

    number8 = new JButton("8");
    number8.setBounds(xPosition + SPACE * 2, yPosition + SPACE * 2, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(number8);

    number9 = new JButton("9");
    number9.setBounds(xPosition + SPACE * 3, yPosition + SPACE * 2, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(number9);

    delete = new JButton("Del");
    delete.setBounds(xPosition + SPACE * 4, yPosition + SPACE * 2, BUTTON_WIDTH, BUTTON_HEIGHT);
    setUpButton(delete);

    submit = new JButton("Submit");
    equals = new JButton("=");
    if (mode.equals("EASY") || mode.equals("HARD")) {
      submit.setBounds(xPosition + SPACE/2, yPosition + SPACE * 3, 335, BUTTON_HEIGHT);
      setUpButton(submit);
    } else {
      submit.setBounds(xPosition, yPosition + SPACE * 3, 335, BUTTON_HEIGHT);
      setUpButton(submit);
      equals.setBounds(xPosition + SPACE * 4, yPosition + SPACE * 3, BUTTON_WIDTH, BUTTON_HEIGHT);
      setUpButton(equals);
    }
  }

  /**
   * Sets up the main frame.
   */
  public void setupFrame() {
    mainFrame = new JFrame("Numble");
    layeredPane = new JLayeredPane();
    layeredPane.setBounds(0, 0, 1000, 1000);
    mainFrame.add(layeredPane);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    mainFrame.setResizable(false);
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - mainFrame.getWidth()) / 2);
    int y = (int) ((dimension.getHeight() - mainFrame.getHeight()) / 2);
    mainFrame.setLocation(x, y);
    mainFrame.setVisible(true);
  }

  /**
   * Sets up a button.
   * @param button  the button to be set up
   */
  public void setUpButton(JButton button) {
    button.setBackground(new Color(251,251,249));
    button.setForeground(Color.black);
    button.setBorder(new LineBorder(Color.lightGray));
    button.setFont(new Font("Arial", Font.BOLD, 18));
    button.setFocusPainted(false);
    calculator.add(button);
    mainFrame.add(button);
  }

  /**
   * Sets the colour of a button
   * @param button  the button to be coloured
   * @param color  the colour of the button
   */
  public void setColor(JButton button, Color color) {
    button.setBackground(color);
    button.setBorder(new LineBorder(color));
  }

  public JButton getNumber0() {
    return number0;
  }

  public JButton getNumber1() {
    return number1;
  }

  public JButton getNumber2() {
    return number2;
  }

  public JButton getNumber3() {
    return number3;
  }

  public JButton getNumber4() {
    return number4;
  }

  public JButton getNumber5() {
    return number5;
  }

  public JButton getNumber6() {
    return number6;
  }

  public JButton getNumber7() {
    return number7;
  }

  public JButton getNumber8() {
    return number8;
  }

  public JButton getNumber9() {
    return number9;
  }

  public JButton getPlus() {
    return plus;
  }

  public JButton getMinus() {
    return minus;
  }

  public JButton getMultiply() {
    return multiply;
  }

  public JButton getDivide() {
    return divide;
  }

  public JButton getDelete() {
    return delete;
  }

  public JButton getEquals() {
    return equals;
  }

  public JButton getSubmit() { return submit; }

  public ArrayList<JButton> getCalculator() {
    return calculator;
  }

  public GridController getGridView() {
    return gameGrid;
  }
}
