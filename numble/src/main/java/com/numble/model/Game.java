package com.numble.model;

import com.numble.GameInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class Game implements GameInterface {
  private String target = null;
  private String targetResult = null;
  private ArrayList<String> remainingCharList;
  private ArrayList<String> colourCode;
  private Equation.Pair<String, String> equationResultPair;

  public Game() {
    equationResultPair = Equation.getEquationResultPair();
    target = (String) equationResultPair.getEquation();
    targetResult = (String) equationResultPair.getResult();
    remainingCharList = new ArrayList<>();
  }

  public Game(String target, String result) {
    this.target = target;
    this.targetResult = result;
    remainingCharList = new ArrayList<>();
  }

  @Override
  public ArrayList<String> checkGuess(ArrayList<String> userGuessArray) {
    String[] targetArray = target.split("");
    ArrayList<String> targetList = new ArrayList<>(Arrays.asList(targetArray));
    colourCode = new ArrayList<String>();

    initilizeColours(targetList);
    setGreenTiles(userGuessArray, targetList);
    setOrangeTiles(userGuessArray, targetList);

    return colourCode;
  }
  
  void initilizeColours(ArrayList<String> targetList) {
    for (int i = 0; i < targetList.size(); i++) {
      colourCode.add("GREY");
    }
  }

  void setGreenTiles(ArrayList<String> userGuessArray, ArrayList<String> targetList) {
    for (int i = 0; i < userGuessArray.size(); i++) {
      String number = userGuessArray.get(i);
      String targetNumber = targetList.get(i);
      if (number.equals(targetNumber)) {
        colourCode.set(i, "GREEN");
        targetList.set(i, "*");
      } else {
        remainingCharList.add(userGuessArray.get(i));
      }
    }
  }

  void setOrangeTiles(ArrayList<String> userGuessArray, ArrayList<String> targetList) {
    for (int i = 0; i < userGuessArray.size(); i++) {
      String number = userGuessArray.get(i);
      boolean correct = colourCode.get(i).equals("GREEN");
      if (!correct && targetList.contains(number)) {
        colourCode.set(i, "ORANGE");
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
      return !(colourCode.contains("ORANGE") || (colourCode.contains("GREY")));
    }
  }

  @Override
  public String getTarget() {
    return target;
  }

  @Override
  public String getTargetResult() {
    return targetResult;
  }
}
