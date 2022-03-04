package com.numble.model;

import javax.sound.midi.SysexMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

//does the need to be public *static* class?
public class Equation {

  //redundant if getEquationResultPairFromDoc is okay
  static List<String> equations = new ArrayList<>(Arrays.asList("10+10", "63x2", "54x3", "10+1", "1+1"));
  static List<String> equationResults = new ArrayList<>(Arrays.asList("=20", "=126", "=162", "=11", "=2"));

  //redundant if getEquationResultPairFromDoc is okay
  static Pair<String, String> getEquationResultPair() {
    int index = getRandomIndex();
    return new Pair<String, String>(equations.get(index), equationResults.get(index));
  }

  //redundant if getEquationResultPairFromDoc is okay
  static int getRandomIndex() {
    Random random = new Random();
    return random.nextInt(equations.size() - 1);
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

  static Pair<String, String> getEquationResultPairFromDoc() {
    String fullEquation = getRandomEquationFromArray(readEquationsDoc());
    String target = fullEquation.substring(0, fullEquation.indexOf('='));
    String result = fullEquation.substring(fullEquation.indexOf('='));
    return new Pair<String, String>(target, result);
  }

  //method to get an equation from word file
  static String[] readEquationsDoc() {
    //NEED TO MAKE RELATIVE PATH
    File file = new File("../../project-code/Equations.txt");
    String allEquations = "";

    try (FileReader fr = new FileReader(file))
    {
      int content;
      while ((content = fr.read()) != -1) {
        char currentChar = (char) content;
        allEquations = allEquations + currentChar;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    String[] equations = allEquations.split("\\R");
    return equations;
  }

  static String getRandomEquationFromArray(String[] array) {
    Random random = new Random();
    int randomInt = random.nextInt(array.length);
    return array[randomInt];
  }


}
