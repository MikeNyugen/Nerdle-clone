package com.numble.model;

import com.numble.GameInterface;
import com.numble.evaluator.Evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Model of the game class
 */
public class Game implements GameInterface {
  private String target = null;
  private String targetResult = null;
  private final ArrayList<String> remainingCharList;
  private ArrayList<Colour> colourCode;
  private int guessesRemaining = 5;
  private Mode gameMode;

  /**
   * Constructor to create a game which selects an equation from file
   *
   * @param mode the game mode
   */
  public Game(String mode) {
    Equation.Pair<String, String> equationResultPair = Equation.getEquationResultPairFromDoc();
    target = equationResultPair.getEquation();
    targetResult = equationResultPair.getResult();
    remainingCharList = new ArrayList<>();
    setGameMode(mode);
  }

  /**
   * Constructor where you can set what needs guessing, the result, and game mode
   *
   * @param target the part of the equation which the user must guess
   * @param result the result of the equation
   * @param mode   the game difficulty
   */
  public Game(String target, String result, String mode) {
    this.target = target;
    this.targetResult = result;
    remainingCharList = new ArrayList<>();
    setGameMode(mode);
  }

  /**
   * Constructor where default mode is easy
   *
   * @param target part of the equation which the user must guess
   * @param result the result of the equation
   */
  public Game(String target, String result) {
    this(target, result, "EASY");
  }

  /**
   * Method to check the user guess
   *
   * @param userGuessArray an array of each character the user enters in their guess
   * @return an array list of colours which represent the "success" of each character guess
   */
  @Override
  public ArrayList<Colour> checkGuess(ArrayList<String> userGuessArray) {
    String[] targetArray = target.split("");
    ArrayList<String> targetList = new ArrayList<>(Arrays.asList(targetArray));
    colourCode = new ArrayList<>();

    initializeColours(targetList);
    setGreenTiles(userGuessArray, targetList);
    setOrangeTiles(userGuessArray, targetList);

    if ((gameMode.equals(Mode.HARD)) || (gameMode.equals(Mode.SUPERHARD))) {
      if (!doesItResultInCorrectSolution(userGuessArray)) {
        for (int i = 0; i < userGuessArray.size(); i++) {
          colourCode.set(i, Colour.PURPLE);
        }
      }
    }
    guessesRemaining--;
    return colourCode;
  }

  /**
   * initializes initial array of colours, length of the target, default colour grey
   *
   * @param targetList list of characters the user must guess
   */
  void initializeColours(ArrayList<String> targetList) {
    for (int i = 0; i < targetList.size(); i++) {
      colourCode.add(Colour.GREY);
    }
  }

  /**
   * checks which character guesses are exactly correct
   *
   * @param userGuessArray array list of the users guess
   * @param targetList     array list which the user must guess
   */
  void setGreenTiles(ArrayList<String> userGuessArray, ArrayList<String> targetList) {
    for (int i = 0; i < userGuessArray.size(); i++) {
      String number = userGuessArray.get(i);
      String targetNumber = targetList.get(i);
      if (number.equals(targetNumber)) {
        colourCode.set(i, Colour.GREEN);
        targetList.set(i, "*");
      } else {
        remainingCharList.add(userGuessArray.get(i));
      }
    }
  }

  /**
   * checks which character guesses are partially correct
   *
   * @param userGuessArray array list of the users guess
   * @param targetList     array list which the user must guess
   */
  void setOrangeTiles(ArrayList<String> userGuessArray, ArrayList<String> targetList) {
    for (int i = 0; i < userGuessArray.size(); i++) {
      String number = userGuessArray.get(i);
      boolean correct = colourCode.get(i).equals(Colour.GREEN);
      if (!correct && targetList.contains(number)) {
        colourCode.set(i, Colour.ORANGE);
        remainingCharList.remove(userGuessArray.get(i));
        targetList.remove(userGuessArray.get(i));
      }
    }
  }

  /**
   * checks whether the user has won or not
   *
   * @return true if the user has won
   */
  @Override
  public boolean hasWon() {
    if (colourCode == null) {
      return false;
    } else {
      return Collections.frequency(colourCode, Colour.GREEN) == colourCode.size();
    }
  }

  /**
   * checks whether the user has lost or not
   *
   * @return true if the user has lost
   */
  @Override
  public boolean hasLost() {
    return (guessesRemaining == 0) && (!hasWon());
  }

  /**
   * method to get the target of this game
   *
   * @return what the user should be guessing
   */
  @Override
  public String getTarget() {
    return target;
  }

  /**
   * method to get the result of the equation
   *
   * @return the result of the equation
   */
  @Override
  public String getTargetResult() {
    return targetResult;
  }

  /**
   * checks if the users guess results in the result they are guessing for
   *
   * @param userGuessArray array of the users guess
   * @return true if the equation the user has guessed results in the desired solution
   */
  public boolean doesItResultInCorrectSolution(ArrayList<String> userGuessArray) {
    StringBuilder guess = new StringBuilder();
    int i;
    for (i = 0; i < userGuessArray.size(); i++) {
      var s = userGuessArray.get(i);
      if (s.equals("=")) {
        break;
      }
      guess.append(s);
    }
    var guessResult = Evaluator.evaluate(String.valueOf(guess));
    if (gameMode == Mode.EASY || gameMode == Mode.HARD) {
      if (targetResult.charAt(0) == '=') {
        return Integer.valueOf(targetResult.substring(1)).equals(guessResult);
      } else {
        return Integer.valueOf(targetResult).equals(guessResult);
      }
    } else {
      StringBuilder userResult = new StringBuilder();
      for (i = i + 1; i < userGuessArray.size(); i++) {
        if (!userGuessArray.get(i).equals("=")) {
          userResult.append(userGuessArray.get(i));
        }
      }
      return Integer.valueOf(userResult.toString()).equals(guessResult);
    }
  }

  /**
   * adds equals sing onto part which the user must guess
   */
  public void addEqualsOntoTarget() {
    target = target + "=" + targetResult;
  }

  /**
   * method to set the game mode
   *
   * @param gameModeIn game mode
   */
  private void setGameMode(String gameModeIn) {
    switch (gameModeIn) {
      case "EASY":
        gameMode = Mode.EASY;
        break;
      case "MEDIUM":
        gameMode = Mode.MEDIUM;
        addEqualsOntoTarget();
        break;
      case "HARD":
        gameMode = Mode.HARD;
        break;
      case "SUPERHARD":
        gameMode = Mode.SUPERHARD;
        addEqualsOntoTarget();
        break;
      default:
        gameMode = Mode.EASY;
    }
  }
}
