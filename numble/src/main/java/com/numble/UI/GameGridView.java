package com.numble.UI;

import javax.swing.*;
import java.awt.*;

public class GameGridView extends JComponent {
  // need to include method to change cols depending on length of equation
  int rows = 5;
  int cols = 5;

  public GameGridView() {
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.gray);
    drawGrid(g2d);
  }

  /**
   * Very naive implementation. Values are hard-coded.
   * Grid does not center based on the number of columns.
   * @param g2d
   */
  public void drawGrid(Graphics2D g2d) {
    int xPosition = 295;
    int yPosition = 50;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        g2d.fillRoundRect(xPosition, yPosition, 80,80,10,10);
        xPosition += 90;
      }
      yPosition += 90;
      xPosition = 295;
    }
  }
}

