package com.numble.model;

/**
 * Represents a cell in the game grid.
 */
public class Cell {
  String value;
  int xPosition;
  int yPosition;
  Colour colour;

  /**
   * Initialises a cell with a blank value and sets the colour to white.
   * @param xPosition  the x position of the cell
   * @param yPosition  the y position of the cell
   */
  public Cell(int xPosition, int yPosition) {
    this.xPosition = xPosition;
    this.yPosition = yPosition;
    value = "";
    colour = Colour.WHITE;
  }

  public int getxPosition() {
    return xPosition;
  }

  public int getyPosition() {
    return yPosition;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setColour(Colour colour) {
    this.colour = colour;
  }

  public Colour getColour() {
    return this.colour;
  }
}


