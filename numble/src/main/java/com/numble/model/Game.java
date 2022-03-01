package com.numble.model;

import com.numble.GameInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class Game implements GameInterface {

  // attributes
  private String target = null;
  private String targetResult = null;
  private ArrayList<Integer> colourCode;
  private Equation.Pair<String, String> equationResultPair;
  private ArrayList<String> remainingCharList;

  // methods
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
  public ArrayList<Integer> checkGuess(ArrayList<String> userGuessArray) {
    // The target as an array list to compare against the userGuessArray
    String[] targetArray = target.split("");
    ArrayList<String> targetList = new ArrayList<>(Arrays.asList(targetArray));
    colourCode = new ArrayList<Integer>();

    initilizeColours(targetList);
    setGreenTiles(userGuessArray, targetList);
    setOrangeTiles(userGuessArray, targetList);

    return colourCode;
  }

  void initilizeColours(ArrayList<String> targetList) {
    // Initialize colour code so that it is the length of the target, default values
    // being 2
    for (int i = 0; i < targetList.size(); i++) {
      colourCode.add(2);
    }
  }

  void setGreenTiles(ArrayList<String> userGuessArray, ArrayList<String> targetList) {
    // Loop through the guess to identify indexes which are correct (change in
    // colour code), and add any which aren't exactly correct to remaining character
    // list
    for (int i = 0; i < userGuessArray.size(); i++) {
      String number = userGuessArray.get(i);
      String targetNumber = targetList.get(i);
      if (number.equals(targetNumber)) {
        colourCode.set(i, 0);
        targetList.set(i, "*");
      } else {
        remainingCharList.add(userGuessArray.get(i));
      }
    }
  }

  void setOrangeTiles(ArrayList<String> userGuessArray, ArrayList<String> targetList) {
    // Loop to identify present, but wrong place
    for (int i = 0; i < userGuessArray.size(); i++) {
      String number = userGuessArray.get(i);
      boolean correct = colourCode.get(i) == 0;
      if (!correct && targetList.contains(number)) {
        colourCode.set(i, 1);
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
      return !(colourCode.contains(1) || (colourCode.contains(2)));
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
