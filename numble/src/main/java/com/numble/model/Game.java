package com.numble.model;

import com.numble.GameInterface;
import com.fathzer.soft.javaluator.DoubleEvaluator;

import java.util.ArrayList;
import java.util.Arrays;

public class Game implements GameInterface {
  private String target = null;
  private String targetResult = null;
  private ArrayList<String> remainingCharList;
  private ArrayList<Colour> colourCode;
  private int guessesRemaining = 5;

  public Game() {
    Equation.Pair<String, String> equationResultPair = Equation.getEquationResultPairFromDoc();
    target = equationResultPair.getEquation();
    targetResult = equationResultPair.getResult();
    remainingCharList = new ArrayList<>();
  }

  public Game(String target, String result) {
    this.target = target;
    this.targetResult = result;
    remainingCharList = new ArrayList<>();
  }

  @Override
  public ArrayList<Colour> checkGuess(ArrayList<String> userGuessArray) {
    String[] targetArray = target.split("");
    ArrayList<String> targetList = new ArrayList<>(Arrays.asList(targetArray));
    colourCode = new ArrayList<Colour>();

    initializeColours(targetList);
    setGreenTiles(userGuessArray, targetList);
    setOrangeTiles(userGuessArray, targetList);

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
      return !(colourCode.contains(Colour.ORANGE) || (colourCode.contains(Colour.GREY)));
    }
  }

  @Override
  public boolean hasLost() {
    if ((guessesRemaining == 0) && (!hasWon())) {
      return true;
    } else {
      return false;
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

  public boolean doesItResultInCorrectSolution(String guess) {
    DoubleEvaluator evaluator = new DoubleEvaluator();
    Double guessResult = evaluator.evaluate(guess);
    return (Double.parseDouble(targetResult) == guessResult);
  }
}
