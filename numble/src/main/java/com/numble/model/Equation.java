package com.numble.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

//does the need to be public *static* class?
public class Equation {
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

  static Pair<String, String> getEquationResultPairFromDoc() {
    String fullEquation = getRandomEquationFromArray(readEquationsDoc());
    String target = fullEquation.substring(0, fullEquation.indexOf('='));
    String result = fullEquation.substring(fullEquation.indexOf('='));
    System.out.println(target + "   " + result);
    return new Pair<>(target, result);
  }

  //method to get an equation from word file
  static String[] readEquationsDoc() {
    //NEED TO MAKE RELATIVE PATH
    File file = new File("../../project-code/Equations.txt");
    StringBuilder str = new StringBuilder();

    try (FileReader fr = new FileReader(file)) {
        int content;
        while ((content = fr.read()) != -1) {
          char currentChar = (char) content;
          str.append(currentChar);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return str.toString().split("\\R");
  }

  static String getRandomEquationFromArray(String[] array) {
    Random random = new Random();
    int randomInt = random.nextInt(array.length);
    return array[randomInt];
  }
}
