package com.numble.model;

import com.numble.GameInterface;
import com.numble.evaluator.Evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Model of the game class
 */
public class Game implements GameInterface {
  private String target = null;
  private String targetResult = null;
  private final ArrayList<String> remainingCharList;
  private ArrayList<Colour> colourCode;
  private int guessesRemaining = 5;
  private StringBuilder result;

  public Mode getGameMode() {
    return gameMode;
  }

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
  public List<Colour> checkGuess(List<String> userGuessArray) {
    String[] targetArray = target.split("");
    ArrayList<String> targetList = new ArrayList<>(Arrays.asList(targetArray));
    colourCode = new ArrayList<>();

    initializeColours(targetList);
    setGreenTiles(userGuessArray, targetList);
    setOrangeTiles(userGuessArray, targetList);

    if ((gameMode.equals(Mode.HARD)) || (gameMode.equals(Mode.SUPERHARD))) {
      if (!checkEquation(userGuessArray)) {
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
  void initializeColours(List<String> targetList) {
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
  void setGreenTiles(List<String> userGuessArray, List<String> targetList) {
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
  void setOrangeTiles(List<String> userGuessArray, List<String> targetList) {
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

  public boolean checkEquation(List<String> userGuessArray){
    StringBuilder guess = new StringBuilder();
    StringBuilder first = new StringBuilder();
    result = new StringBuilder();
    int nonDigits = 0;
    int equals = 0;
    if (gameMode == Mode.HARD) {
      for (String s : userGuessArray) {
        if (Character.isDigit(s.charAt(0))) {
          guess.append(s);
        } else {
          guess.append(s);
          nonDigits++;
        }
      }
      return nonDigits == 1 && Character.isDigit(guess.charAt(0)) && Character.isDigit(guess.charAt(guess.toString().length() - 1));
    } else if (gameMode == Mode.SUPERHARD) {
      boolean onResult = false;
      for (String s : userGuessArray) {
        if (s.charAt(0) == '=' && equals == 0) {
          onResult = true;
          equals++;
          result.append(s);
        } else if (Character.isDigit(s.charAt(0)) && !onResult) {
          first.append(s);
        } else if (Character.isDigit(s.charAt(0)) && onResult) {
          result.append(s);
        } else {
          if (equals == 1) {
            result.append(s);
          } else {
            first.append(s);
          }
          nonDigits++;
        }
      }
    }
    return nonDigits == 1 && equals == 1 && Character.isDigit(first.charAt(0)) && Character.isDigit(result.charAt(result.toString().length() - 1));
  }

  public boolean checkResultsEqual(StringBuilder result) {
    String resultOnly;
    if (targetResult.startsWith("=")) {
      resultOnly = targetResult.substring(1);
    } else {
      resultOnly = targetResult;
    }
    return resultOnly.equals(result.toString());
  }

  /**
   * adds equals sign onto part which the user must guess
   */
  public void addEqualsOntoTarget() {
    target = target + targetResult;
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
