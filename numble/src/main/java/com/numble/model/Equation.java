package com.numble.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equation {

  List<String> equations = new ArrayList<>(Arrays.asList("10+10=20", "63x2=126", "54x3=162", "10+1=11", "1+1=2"));

  String getRandomWord() {
    String randomEquation = equations.get((int) (Math.random() * equations.size()));
    return randomEquation;
  }

}
