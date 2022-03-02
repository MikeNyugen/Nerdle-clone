package com.numble.UI;

import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
  private final static int FRAME_WIDTH = 1000;
  private final static int FRAME_HEIGHT = 1000;
  private final static int BUTTON_WIDTH = 80;
  private final static int BUTTON_HEIGHT = 80;
  private final static int SPACE = 85;

  JFrame mainFrame;

  public GameView() {
    mainFrame = new JFrame("Numble");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    mainFrame.setVisible(true); // display frame
    GameGridView gameGrid = new GameGridView();
    initializeButtons();
    mainFrame.add(gameGrid);
    mainFrame.paintAll(mainFrame.getGraphics());
  }

  private void initializeButtons() {
    int xPosition = 305;
    int yPosition = 540;
    Button number0 = new Button("0");
    number0.setBounds(xPosition,yPosition, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(number0);

    Button number1 = new Button("1");
    number1.setBounds(xPosition + SPACE,yPosition, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(number1);

    Button number2 = new Button("2");
    number2.setBounds(xPosition + SPACE*2,yPosition, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(number2);

    Button plus = new Button("+");
    plus.setBounds(xPosition + SPACE*3,yPosition, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(plus);

    Button multiply = new Button("x");
    multiply.setBounds(xPosition + SPACE*4,yPosition, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(multiply);

    Button number3 = new Button("3");
    number3.setBounds(xPosition,yPosition + SPACE, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(number3);

    Button number4 = new Button("4");
    number4.setBounds(xPosition + SPACE,yPosition + SPACE, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(number4);

    Button number5 = new Button("5");
    number5.setBounds(xPosition + SPACE*2,yPosition + SPACE, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(number5);

    Button minus = new Button("-");
    minus.setBounds(xPosition + SPACE*3,yPosition + SPACE, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(minus);

    Button divide = new Button("/");
    divide.setBounds(xPosition + SPACE*4,yPosition + SPACE, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(divide);

    Button number6 = new Button("6");
    number6.setBounds(xPosition,yPosition + SPACE*2, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(number6);

    Button number7 = new Button("7");
    number7.setBounds(xPosition + SPACE,yPosition + SPACE*2, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(number7);

    Button number8 = new Button("8");
    number8.setBounds(xPosition + SPACE*2,yPosition + SPACE*2, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(number8);

    Button number9 = new Button("9");
    number9.setBounds(xPosition + SPACE*3,yPosition+ SPACE*2, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(number9);

    Button delete = new Button("Del");
    delete.setBounds(xPosition + SPACE*4,yPosition + SPACE*2, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(delete);

    Button equals = new Button("=");
    equals.setBounds(xPosition + SPACE*4,yPosition + SPACE*3, BUTTON_WIDTH,BUTTON_HEIGHT);
    mainFrame.add(equals);

    Button submit = new Button("Submit");
    submit.setBounds(xPosition, yPosition + SPACE*3, 335, BUTTON_HEIGHT);
    mainFrame.add(submit);
  }
}
