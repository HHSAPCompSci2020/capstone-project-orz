import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import processing.core.PApplet;

public class Grid {

    private Cell[][] grid;
    private int[] playerLocation;
    private Cell cellUnderPlayer;

    public Grid() {
        this.grid = new Cell[20][20];
        this.cellUnderPlayer = new Cell();
        this.playerLocation = new int[2];
        Arrays.fill(playerLocation, -1);
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] instanceof PlayerCell) {
                    playerLocation[0] = r;
                    playerLocation[1] = c;
                    break;
                }
            }
            if (playerLocation[0] != -1) {
                break;
            }
        }
    }

    public Grid(int height, int width, String filename) {
        grid = new Cell[height][width];
        this.cellUnderPlayer = new Cell();
        this.readData(filename, grid);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == null) {
                    grid[i][j] = new Cell();
                }

            }
        }
        this.playerLocation = new int[2];
        Arrays.fill(playerLocation, -1);
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] instanceof PlayerCell) {
                    playerLocation[0] = r;
                    playerLocation[1] = c;
                    break;
                }
            }
            if (playerLocation[0] != -1) {
                break;
            }
        }
    }
    
    public boolean hasReachedBuilding(char targetBuildingChar) {
    	if (this.cellUnderPlayer instanceof EntranceCell) {
    		char buildingChar = ((EntranceCell) this.cellUnderPlayer).getBuildingChar();
    		if (buildingChar == targetBuildingChar) {
    			return true;
    		}
    	}
    	return false;
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

                marker.pushStyle();
                marker.fill(grid[r][c].fillColor()[0], grid[r][c].fillColor()[1], grid[r][c].fillColor()[2]);
                marker.rect(rectX, rectY, rectWidth, rectHeight);
                marker.popStyle();

            }
        }
        marker.fill(0);
    }

    List<Cell> findShortestPath(Cell[][] grid, Cell start, Cell end) {
        int rLen = grid.length;
        int cLen = grid[0].length;
        int[] startCords = null;
        for (int r = 0; r < rLen; r++) {
            for (int c = 0; c < cLen; c++) {
                if (grid[r][c] == start) {
                    startCords = new int[] { r, c };
                    break;
                }
            }
            if (startCords != null) {
                break;
            }
        }
        boolean[][] visited = new boolean[rLen][cLen];
        Stack<Cell> shortestPath = new Stack<>();
        Stack<Cell> currPath = new Stack<>();
        dfs(grid, visited, shortestPath, currPath, startCords[0], startCords[1], end);
        return new ArrayList<>(shortestPath);
    }

    void dfs(Cell[][] grid, boolean[][] visited, Stack<Cell> shortestPath, Stack<Cell> currPath, int r, int c,
            Cell target) {
        if (!shortestPath.isEmpty() && shortestPath.size() <= currPath.size()) {
            return;
        }
        visited[r][c] = true;
        currPath.push(grid[r][c]);
        if (grid[r][c] == target) {
            if (shortestPath.isEmpty() || currPath.size() < shortestPath.size()) {
                shortestPath.clear();
                currPath.stream().forEach(cell -> shortestPath.push(cell));
            }
            visited[r][c] = false;
            currPath.pop();
            return;
        }
        final int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        for (int[] dir : directions) {
            int nr = r + dir[0];
            int nc = c + dir[1];
            if (inBounds(grid, nr, nc) && !visited[nr][nc] && grid[nr][nc].isTraversable()) {
                dfs(grid, visited, shortestPath, currPath, nr, nc, target);
            }
        }
        visited[r][c] = false;
        currPath.pop();
    }

    boolean inBounds(Cell[][] grid, int r, int c) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) {
            return false;
        }
        return true;
    }
    
    public void setPlayerLoc(int r, int c) {
    	if (inBounds(grid, r, c) && grid[r][c].isTraversable()) {
            int pr = playerLocation[0];
            int pc = playerLocation[1];
            Cell playerCell = grid[pr][pc];
            grid[pr][pc] = cellUnderPlayer;
            cellUnderPlayer = grid[r][c];
            grid[r][c] = playerCell;
            playerLocation[0] = r;
            playerLocation[1] = c;
        }
    }

    public void movePlayerUp() {
        int r = playerLocation[0];
        int c = playerLocation[1];
        int[] dir = { -1, 0 };
        int nr = r + dir[0];
        int nc = c + dir[1];
        setPlayerLoc(nr, nc);
    }

    public void movePlayerDown() {
        int r = playerLocation[0];
        int c = playerLocation[1];
        int[] dir = { 1, 0 };
        int nr = r + dir[0];
        int nc = c + dir[1];
        setPlayerLoc(nr, nc);
    }

    public void movePlayerLeft() {
        int r = playerLocation[0];
        int c = playerLocation[1];
        int[] dir = { 0, -1 };
        int nr = r + dir[0];
        int nc = c + dir[1];
        setPlayerLoc(nr, nc);
    }

    public void movePlayerRight() {
        int r = playerLocation[0];
        int c = playerLocation[1];
        int[] dir = { 0, 1 };
        int nr = r + dir[0];
        int nc = c + dir[1];
        setPlayerLoc(nr, nc);
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
                CellFactory cellFactory = new CellFactory();
                while (in.hasNext()) {
                    String line = in.nextLine();
                    for (int i = 0; i < line.length(); i++)
                        if (count < gameData.length && i < gameData[count].length) {
                        	char c = line.charAt(i);
                        	Cell newCell = cellFactory.generateCell(c);
                        	gameData[count][i] = newCell;
                        }
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
