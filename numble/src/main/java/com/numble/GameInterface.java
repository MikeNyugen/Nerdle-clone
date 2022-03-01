package com.numble;

import java.util.ArrayList;

public interface GameInterface {

  /**
   * method to check the user's guess
   * @param userGuess the user guess
   * @return array list in which each value corresponds to the state of that guess
   * (e.g. a 0 in position 1 means that the guess in that position is correct)
   * (a 1 in position 1 means that the guess in position 1 is present but not in that location)
   * (a 2 in position 1 means that the guess in position 1 is not present at all)
   */
  ArrayList<Integer> checkGuess(ArrayList<String> userGuess);

  /**
   * method to check if the user has won or not
   * @return true if the user has won
   */
  boolean hasWon();

  /**
   * method to return the equation which the user must guess.
   * This equation ("target") has been generated by generateEquation.
   * @return string equation to guess.
   */
  String getTarget();


}
