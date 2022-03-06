package com.numble.model;

public class Cell {
  String value;
  boolean occupied;
  int xPosition;
  int yPosition;
  Colour colour;

  public Cell(int xPosition, int yPosition) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    value = " ";
    occupied = false;
    colour = Colour.GREY;
  }

  public Cell(int x, int y, String value) {
    this.xPosition = x;
    this.yPosition = y;
    this.value = value;
    occupied = true;
    colour = Colour.GREY;
  }

  public int getxPosition() {
    return xPosition;
  }

  public void setxPosition(int xPosition) {
    this.xPosition = xPosition;
  }

  public int getyPosition() {
    return yPosition;
  }

  public void setyPosition(int yPosition) {
    this.yPosition = yPosition;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public boolean getOccupied() {
    return occupied;
  }

  public void setOccupied(boolean occupied) {
    this.occupied = occupied;
  }

  public void setColour(Colour colour) {
    this.colour = colour;
  }

  public Colour getColour() {
    return this.colour;
  }
}


