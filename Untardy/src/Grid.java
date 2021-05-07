import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import processing.core.PApplet;

public class Grid {

    private Cell[][] grid;

    public Grid() {
        grid = new Cell[20][20];
    }

    public Grid(int height, int width, String filename) {
        grid = new Cell[height][width];
        this.readData(filename, grid);
    }

    // Method used from GridTemplate.java in Recursion2DArrays lab
    public void draw(PApplet marker, float x, float y, float width, float height) {
        marker.noFill();
        final int rLen = grid.length;
        final int cLen = grid[0].length;
        for (int r = 0; r < rLen; r++) {
            for (int c = 0; c < cLen; c++) {

                float rectWidth = width / cLen;
                float rectHeight = height / rLen;
                float rectX = x + c * rectWidth;
                float rectY = y + r * rectHeight;

                marker.rect(rectX, rectY, rectWidth, rectHeight);

                marker.pushStyle();
                marker.fill(0);
                marker.textSize(12);
                marker.textAlign(marker.CENTER, marker.CENTER);
                marker.text(grid[r][c].displayTest(), rectX + (rectWidth / 2), rectY + (rectHeight / 2));
                marker.popStyle();
            }

        }
    }

    // Method used from GridTemplate.java in Recursion2DArrays lab
    public void readData(String filename, Cell[][] gameData) {
        File dataFile = new File(filename);

        if (dataFile.exists()) {
            int count = 0;

            FileReader reader = null;
            Scanner in = null;
            try {
                reader = new FileReader(dataFile);
                in = new Scanner(reader);

                while (in.hasNext()) {
                    String line = in.nextLine();
                    for (int i = 0; i < line.length(); i++)
                        if (count < gameData.length && i < gameData[count].length)
                            gameData[count][i] = new Cell(line.charAt(i));
                    count++;

                }

            } catch (IOException ex) {
                throw new IllegalArgumentException("Data file " + filename + " cannot be read.");
            } finally {
                if (in != null)
                    in.close();
            }

        } else {
            throw new IllegalArgumentException("Data file " + filename + " does not exist.");
        }
    }

}
