package com.numble.UI;

import com.numble.NumbleClient;
import com.numble.model.Cell;
import com.numble.model.Colour;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GridController extends JComponent {
  ArrayList<ArrayList<Cell>> grid;
  int rows = 5;
  int columns;
  int cellWidth = 80;
  int spacing = 10;
  int xPosition;
  int yPosition;

  public GridController(int gameID, NumbleClient client) throws URISyntaxException, IOException, ParseException, InterruptedException {
    this.grid = new ArrayList<>();
    columns = client.getTargetResult(gameID).length() + client.getTargetLength(gameID);
    initializeGrid();
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.white);
    g2d.setStroke(new BasicStroke(3));
    RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHints(rh);
    drawGrid(g2d);
  }

  public void drawGrid(Graphics2D g2d) {
    xPosition = calculateXPos();
    yPosition = 50;
    for (int i = 0; i < rows; i++) {
      for (Cell cell : this.grid.get(i)) {
        if (cell.getColour() == Colour.ORANGE) {
          g2d.setColor(new Color(251, 177, 23));
        } else if (cell.getColour() == Colour.GREEN) {
          g2d.setColor(new Color(0, 163, 108));
        } else if (cell.getColour() == Colour.GREY) {
          g2d.setColor(Color.lightGray);
        } else {
          g2d.setColor(Color.white);
        }
        g2d.fillRoundRect(cell.getxPosition(), cell.getyPosition(),
                80, 80, 10, 10);
        xPosition += cellWidth + spacing;
      }
    }
    yPosition += cellWidth + spacing;
    xPosition = calculateXPos();
  }

  public void initializeGrid() {
    int startX = calculateXPos();
    int startY = 50;
    for (int i = 0; i < 5; i++) {
      ArrayList<Cell> temp = new ArrayList<>();
      for (int j = 0; j < columns; j++) {
        temp.add(new Cell(startX, startY));
        startX += 90;
      }
      grid.add(temp);
      startY += 90;
      startX = calculateXPos();
    }
  }

  int calculateXPos() {
    int startPosition = 0;
    int emptySpace = 1000 - (90 * columns);
    for (int i = 0; i < emptySpace / 2; i++) {
      startPosition++;
    }
    return startPosition;
  }

  public ArrayList<ArrayList<Cell>> getGrid() {
    return grid;
  }
}




