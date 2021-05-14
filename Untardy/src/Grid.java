import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import cells.*;
import processing.core.PApplet;

/**
 * 
 * @author Jerry Wu
 *
 */
public class Grid {

	private Cell[][] grid;
	private int[] playerLocation;
	private Cell cellUnderPlayer;
	private List<int[]> friendCellPositions;

	/**
	 * creates new Grid with default size 20 by 20
	 */
	public Grid() {
		this.grid = new Cell[20][20];
		this.cellUnderPlayer = new PathCell();
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
		
		Random rand = new Random();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] instanceof PathCell) {
					int n = rand.nextInt(15);
					if (n == 0) {
						grid[r][c] = new FriendCell();
					}
				}
			}
		}
		this.friendCellPositions = new ArrayList<>();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] instanceof FriendCell) {
					friendCellPositions.add(new int[] { r, c });
				}
			}
		}
	}

	/**
	 * creates new grid from filename
	 * 
	 * @param height   the row length of grid
	 * @param width    the col length of grid
	 * @param filename populates grid with specific Cell based on filename
	 *                 characters
	 */
	public Grid(int height, int width, String filename) {
		this.grid = new Cell[height][width];
		this.cellUnderPlayer = new PathCell();
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
		
		Random rand = new Random();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] instanceof PathCell) {
					
					// frequency of FriendCell
					int n = rand.nextInt(20);
					if (n == 0) {
						grid[r][c] = new FriendCell();
					}
				}
			}
		}

		this.friendCellPositions = new ArrayList<>();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] instanceof FriendCell) {
					friendCellPositions.add(new int[] { r, c });
				}
			}
		}
	}

	/**
	 * updates all FriendCell positions FriendCell will not update if it is next to
	 * the PlayerCell; mimics interaction / talking
	 */
	public void moveFriendCellPositions() {
		Random rand = new Random();
		final int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		for (int[] pos : friendCellPositions) {
			int r = pos[0];
			int c = pos[1];
			boolean nextToPlayerCell = false;
			for (int[] dir : directions) {
				int nr = r + dir[0];
				int nc = c + dir[1];
				if (inBounds(grid, nr, nc) && grid[nr][nc] instanceof PlayerCell) {
					nextToPlayerCell = true;
					break;
				}
			}
			if (nextToPlayerCell) {
				continue;
			}
			int dirIdx = rand.nextInt(4);
			int[] dir = directions[dirIdx];
			int nr = r + dir[0];
			int nc = c + dir[1];
			if (inBounds(grid, nr, nc) && grid[nr][nc].isTraversable() && !(grid[nr][nc] instanceof EntranceCell)
					&& !(grid[nr][nc] instanceof FriendCell) && !(grid[nr][nc] instanceof PlayerCell)) {
				Cell friendCell = grid[r][c];
				grid[r][c] = grid[nr][nc];
				grid[nr][nc] = friendCell;
				pos[0] = nr;
				pos[1] = nc;
			}
		}
	}

	/**
	 * checks if player is on an EntranceCell which matches the targetBuildingChar
	 * 
	 * @param targetBuildingChar the target building char
	 * @return returns true is cellUnderPlayer is an EntranceCell and it has the
	 *         same buildingChar as targetBuildingChar
	 */
	public boolean hasReachedBuilding(char targetBuildingChar) {
		if (this.cellUnderPlayer instanceof EntranceCell) {
			char buildingChar = ((EntranceCell) this.cellUnderPlayer).getBuildingChar();
			if (buildingChar == targetBuildingChar) {
				return true;
			}
		}
		return false;
	}

	/**
	 * draws grid
	 * 
	 * @param marker PApplet marker
	 * @param x      x-cord of upper left corner
	 * @param y      y-cord of upper left corner
	 * @param width  width of whole grid
	 * @param height height of whole grid
	 */
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
				int[] color = grid[r][c].getColor();
				marker.fill(color[0], color[1], color[2]);
				marker.rect(rectX, rectY, rectWidth, rectHeight);
				marker.popStyle();

			}
		}
		marker.fill(0);
	}

	/**
	 * finds shortest path from Cell start to Cell end
	 * 
	 * @param grid  grid of Cell
	 * @param start start Cell
	 * @param end   end Cell
	 * @return List of Cell that are in the shortest path
	 */
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

	/**
	 * helper method to find shortest path for findShortestPath method
	 * 
	 * @param grid         grid of Cell
	 * @param visited      boolean array to keep track of visited cells
	 * @param shortestPath stores shortest path Cells
	 * @param currPath     stores current path of Cells
	 * @param r            current row index
	 * @param c            current col index
	 * @param target       target Cell
	 */
	private void dfs(Cell[][] grid, boolean[][] visited, Stack<Cell> shortestPath, Stack<Cell> currPath, int r, int c,
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

	/**
	 * returns true if row r and col c are valid indexes of grid
	 * 
	 * @param grid grid of Cells
	 * @param r    row index
	 * @param c    col index
	 * @return returns true if r and c are valid indexes of grid, returns false
	 *         otherwise
	 */
	boolean inBounds(Cell[][] grid, int r, int c) {
		if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) {
			return false;
		}
		return true;
	}

	/**
	 * sets player location to row r and col c if row r and col c are valid indexes
	 * in grid and grid[r][c] is a traversable cell
	 * 
	 * @param r row index
	 * @param c col index
	 */
	public void setPlayerLoc(int r, int c) {
		if (inBounds(grid, r, c) && grid[r][c].isTraversable() && !(grid[r][c] instanceof FriendCell)) {
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

	/**
	 * moves player up if moving up is within the grid and the cell above is
	 * traversable
	 */
	public void movePlayerUp() {
		int r = playerLocation[0];
		int c = playerLocation[1];
		int[] dir = { -1, 0 };
		int nr = r + dir[0];
		int nc = c + dir[1];
		setPlayerLoc(nr, nc);
	}

	/**
	 * moves player down if moving down is within the grid and the cell below is
	 * traversable
	 */
	public void movePlayerDown() {
		int r = playerLocation[0];
		int c = playerLocation[1];
		int[] dir = { 1, 0 };
		int nr = r + dir[0];
		int nc = c + dir[1];
		setPlayerLoc(nr, nc);
	}

	/**
	 * moves player left if moving left is within the grid and the cell to the left
	 * is traversable
	 */
	public void movePlayerLeft() {
		int r = playerLocation[0];
		int c = playerLocation[1];
		int[] dir = { 0, -1 };
		int nr = r + dir[0];
		int nc = c + dir[1];
		setPlayerLoc(nr, nc);
	}

	/**
	 * moves player right if moving right is within the grid and the cell to the
	 * right is traversable
	 */
	public void movePlayerRight() {
		int r = playerLocation[0];
		int c = playerLocation[1];
		int[] dir = { 0, 1 };
		int nr = r + dir[0];
		int nc = c + dir[1];
		setPlayerLoc(nr, nc);
	}

	/**
	 * fills gameData with Cell types which are specified through the characters in
	 * filename
	 * 
	 * @param filename String that contains characters which determine Cell type
	 * @param gameData 2D grid of cells
	 */
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
