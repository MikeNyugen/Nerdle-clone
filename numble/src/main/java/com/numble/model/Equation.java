package com.numble.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Equation {

  static List<String> equations = new ArrayList<>(Arrays.asList("10+10", "63x2", "54x3", "10+1", "1+1"));
  static List<String> equationResults = new ArrayList<>(Arrays.asList("=20", "=126", "=162", "=11", "=2"));

  static Pair<String, String> getEquationResultPair() {
    int index = getRandomIndex();
    return new Pair<String, String>(equations.get(index), equationResults.get(index));
  }

  static int getRandomIndex() {
    return (int) (Math.random() * equations.size());
  }

  //probably redundant now
  static String getEquation(int index) {
    return equations.get(index);
  }

  //probably redundant now
  static String getEquationResult(int index) {
    return equationResults.get(index);
  }

  static class Pair<T1, T2> {
    private final T1 equation;
    private final T2 result;

    public Pair(T1 first, T2 second) {
      this.equation = first;
      this.result = second;
    }

    public T1 getEquation() {
      return equation;
    }

    public T2 getResult() {
      return result;
    }
  }



}
