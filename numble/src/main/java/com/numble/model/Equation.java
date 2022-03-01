package com.numble.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equation {

  static List<String> equations = new ArrayList<>(Arrays.asList("10+10", "63x2", "54x3", "10+1", "1+1"));
  static List<String> equationResults = new ArrayList<>(Arrays.asList("=20", "=126", "=162", "=11", "=2"));

  static int getRandomIndex() {
    return (int) (Math.random() * equations.size());
  }

  static String getEquation(int index) {
    return equations.get(index);
  }

  static String getEquationResult(int index) {
    return equationResults.get(index);
  }

}
