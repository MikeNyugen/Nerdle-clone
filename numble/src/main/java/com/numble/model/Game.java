package com.numble.model;

import com.numble.GameInterface;

import java.util.ArrayList;
import java.util.Arrays;

public class Game implements GameInterface {

  //attributes
  private String target = null;
  private String targetResult = null;
  private ArrayList<Integer> colourCode;
  private Equation.Pair equationResultPair;

  //methods
  public Game() {
    equationResultPair = Equation.getEquationResultPair();
    target = (String) equationResultPair.getEquation();
    targetResult = (String) equationResultPair.getResult();
    colourCode = new ArrayList<Integer>();
  }

  public Game(String target, String result) {
    this.target = target;
    this.targetResult = result;
    colourCode = new ArrayList<Integer>();
  }

  @Override
  public ArrayList<Integer> checkGuess(ArrayList<String> userGuessArray) {

    //The target as an array list to compare against the userGuessArray
    String[] targetArray = target.split("");
    ArrayList<String> targetList = new ArrayList<>(Arrays.asList(targetArray));

    //The colour code to return
  
    //Initialize colour code so that it is the length of the target, default values being 2
    for (int i = 0; i < targetList.size(); i++) {
      colourCode.add(2);
    }
    System.out.println("INIT CC " + colourCode);

    //Loop through the guess to identify indexes which are correct (change in colour code), and add any which aren't exactly correct to remaining character list
    ArrayList<String> remainingCharList = new ArrayList<>();
    for (int i = 0; i<userGuessArray.size(); i++) {
      if (userGuessArray.get(i).equals(targetList.get(i))) {
        colourCode.set(i, 0);
        targetList.set(i, "*");
      } else {
        remainingCharList.add(userGuessArray.get(i));
      }
    }
    System.out.println("FIRST LOOP CC " + colourCode);
    System.out.println(" REMAINING CHAR LIST " + remainingCharList + " TarList " + targetList);

    //Loop to identify present, but wrong place
    for (int i = 0; i< userGuessArray.size(); i++) {
      if ((colourCode.get(i) == 2) && targetList.contains(userGuessArray.get(i))) {
        colourCode.set(i, 1);
        remainingCharList.remove(userGuessArray.get(i));
        targetList.remove(userGuessArray.get(i));
        System.out.println("i " + i + "CC " + colourCode + " RemCharL " + remainingCharList + " TarList " + targetList);
      }
    }
    System.out.println("SECOND LOOP CC " + colourCode);

  System.out.println(colourCode);
    return colourCode;
  }

  @Override
  public boolean hasWon() {
    return colourCode.contains(1) || (colourCode.contains(2));
  }

  @Override
  public String getTarget() {
    return target;
  }

  @Override
  public String getTargetResult() { return targetResult; }

}
