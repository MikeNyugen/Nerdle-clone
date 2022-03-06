package com.numble.model;

import com.numble.GameInterface;
import com.numble.evaluator.Evaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Game implements GameInterface {
  private String target = null;
  private String targetResult = null;
  private ArrayList<String> remainingCharList;
  private ArrayList<Colour> colourCode;
  private int guessesRemaining = 5;
  private Mode gameMode;

  public Game(String mode) {
    Equation.Pair<String, String> equationResultPair = Equation.getEquationResultPair();
    target = (String) equationResultPair.getEquation();
    targetResult = (String) equationResultPair.getResult();
    if (targetResult.charAt(0) == '=') {
      targetResult = targetResult.substring(1);
    }
    remainingCharList = new ArrayList<>();
    setGameMode(mode);
  }

  public Game() {
    this("EASY");
  }

  public Game(String target, String result, String mode) {
    this.target = target;
    this.targetResult = result;
    remainingCharList = new ArrayList<>();
    setGameMode(mode);
  }

  public Game(String target, String result) {
    this(target, result, "EASY");
  }

  @Override
  public ArrayList<Colour> checkGuess(ArrayList<String> userGuessArray) {
    String[] targetArray = target.split("");
    ArrayList<String> targetList = new ArrayList<>(Arrays.asList(targetArray));
    colourCode = new ArrayList<Colour>();

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
  
  void initializeColours(ArrayList<String> targetList) {
    for (int i = 0; i < targetList.size(); i++) {
      colourCode.add(Colour.GREY);
    }
  }

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

  @Override
  public boolean hasWon() {
    if (colourCode == null) {
      return false;
    } else {
      return Collections.frequency(colourCode, Colour.GREEN) == colourCode.size();
    }
  }

  @Override
  public boolean hasLost() {
    return (guessesRemaining == 0) && (!hasWon());
  }

  @Override
  public String getTarget() {
    return target;
  }

  @Override
  public String getTargetResult() {
    return targetResult;
  }

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
      return Integer.valueOf(targetResult).equals(guessResult);
    } else {
      StringBuilder userResult = new StringBuilder();
      for (i = i + 1; i < userGuessArray.size(); i++) {
        userResult.append(userGuessArray.get(i));
      }
      return Integer.valueOf(userResult.toString()).equals(guessResult);
    }
  }

  //For hard mode, user will guess = sign as well
  public void addEqualsOntoTarget() {
    target = target + "=" + targetResult;
  }

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
