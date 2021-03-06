import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import cells.*;
import processing.core.PApplet;

/**
 * 
 * @author Jerry Wu
 *
 */
public class Grid {

	private Cell[][] grid;
	private int[] prevPlayerLocation;
	private int[] playerLocation;
	private Cell cellUnderPlayer;
	private List<int[]> friendCellPositions;

	/**
	 * creates new Grid with default size 20 by 20
	 */
	public Grid() {
		this.friendCellPositions = new ArrayList<>();
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
		this.prevPlayerLocation = new int[] {playerLocation[0], playerLocation[1]};
		setBuildingCellCharsToMatchEntranceCellChars();
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
		this.friendCellPositions = new ArrayList<>();
		this.grid = new Cell[height][width];
		this.cellUnderPlayer = new PathCell();
		this.readData(filename, grid);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j] == null) {
					grid[i][j] = new PathCell();
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
		this.prevPlayerLocation = new int[] {playerLocation[0], playerLocation[1]};
		setBuildingCellCharsToMatchEntranceCellChars();
	}
	
	public boolean playerHasMoved() {
		return !Arrays.equals(this.prevPlayerLocation, this.playerLocation);
	}
	
	public int[] getPlayerLocation() {
		return new int[] {this.playerLocation[0], this.playerLocation[1]};
	}

	/**
	 * set BuildingCell buildingChar to match EntranceCell buildingChar
	 */
	private void setBuildingCellCharsToMatchEntranceCellChars() {
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] instanceof EntranceCell) {
					char entranceCh = ((EntranceCell) grid[r][c]).getBuildingChar();
					setAdjBuildingCellChars(entranceCh, r, c);
				}
			}
		}
	}

	/**
	 * helper method for setBuildingCellCharsToMatchEntranceCellChars to dfs and set
	 * BuildingCell buildingChar to EntranceCell buildingChar
	 * 
	 * @param entranceCh EntranceCell buildingChar
	 * @param r          row index in grid
	 * @param c          column index in grid
	 */
	private void setAdjBuildingCellChars(char entranceCh, int r, int c) {
		final int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		for (int[] dir : directions) {
			int nr = r + dir[0];
			int nc = c + dir[1];
			if (inBounds(grid, nr, nc) && grid[nr][nc] instanceof BuildingCell) {
				char buildingCh = ((BuildingCell) grid[nr][nc]).getBuildingChar();
				if (buildingCh != entranceCh) {
					((BuildingCell) grid[nr][nc]).setBuildingChar(entranceCh);
					setAdjBuildingCellChars(entranceCh, nr, nc);
				}
			}
		}
	}

	/**
	 * randomly turns PathCell into FriendCell based on frequency freq
	 * 
	 * @param freq a double which correlates with the chance of a PathCell turning
	 *             into a FriendCell ex. 0.25 means a PathCell has a 25% chance of
	 *             turning into a FriendCell
	 */
	public void generateFriendCells(double freq) {
		for (int[] pos : this.friendCellPositions) {
			int r = pos[0];
			int c = pos[1];
			grid[r][c] = new PathCell();
		}
		this.friendCellPositions.clear();
		for (int r = 0; r < grid.length; r++) {
			for (int c = 0; c < grid[0].length; c++) {
				if (grid[r][c] instanceof PathCell) {
					if (Math.random() <= freq) {
						grid[r][c] = new FriendCell();
						this.friendCellPositions.add(new int[] { r, c });
					}
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
		Iterator<int[]> itr = this.friendCellPositions.iterator();
		while (itr.hasNext()) {
			int[] pos = itr.next();
			int r = pos[0];
			int c = pos[1];
			if (!(grid[r][c] instanceof FriendCell)) {
				itr.remove();
				continue;
			}
			int pr = this.playerLocation[0];
			int pc = this.playerLocation[1];
			boolean adjToPlayer = false;
			for (int[] dir : directions) {
				int nr = r + dir[0];
				int nc = c + dir[1];
				if (nr == pr && nc == pc) {
					adjToPlayer = true;
					break;
				}
			}
			if (adjToPlayer) {
				continue;
			}

			int dirIdx = rand.nextInt(4);
			int[] dir = directions[dirIdx];
			int nr = r + dir[0];
			int nc = c + dir[1];
			if (inBounds(grid, nr, nc) && grid[nr][nc].isTraversable() && !(grid[nr][nc] instanceof EntranceCell)
					&& !(grid[nr][nc] instanceof PlayerCell)) {
				Cell friendCell = grid[r][c];
				grid[r][c] = grid[nr][nc];
				grid[nr][nc] = friendCell;
				pos[0] = nr;
				pos[1] = nc;
			}
		}
	}

	/**
	 * check if player is blocked in all 4 cardinal directions
	 * 
	 * @return returns true if player is blocked
	 */
	public boolean playerIsBlocked() {
		final int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		int r = this.playerLocation[0];
		int c = this.playerLocation[1];
		int blockedDirCount = 0;
		for (int[] dir : directions) {
			int nr = r + dir[0];
			int nc = c + dir[1];
			if (!inBounds(grid, nr, nc) || !grid[nr][nc].isTraversable()) {
				blockedDirCount++;
			}
		}
		return blockedDirCount == 4;
	}

	/**
	 * changes one random FriendCell that is adjacent to player to a PathCell
	 */
	public void setRandomAdjFriendToPathCell() {
		final int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		int r = this.playerLocation[0];
		int c = this.playerLocation[1];
		for (int[] dir : directions) {
			int nr = r + dir[0];
			int nc = c + dir[1];
			if (inBounds(grid, nr, nc) && grid[nr][nc] instanceof FriendCell) {
				grid[nr][nc] = new PathCell();
				break;
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

				if (grid[r][c] instanceof BuildingCell) {
						marker.fill(0);
						marker.textSize(10);
						marker.textAlign(marker.CENTER, marker.CENTER);
						char buildingChar = ((BuildingCell) grid[r][c]).getBuildingChar();
						marker.text(buildingChar == 0 ? ' ' : buildingChar, rectX + (rectWidth / 2), rectY + (rectHeight / 2));
				}

				marker.popStyle();

			}
		}
		marker.fill(0);
	}

	/**
	 * finds shortest path via BFS
	 * @param grid Cell grid
	 * @param startPos starting position
	 * @param targetEntranceCellBuildingChar target entrance building char
	 * @return returns shortest path move count
	 */
	private List<Cell> findShortestPath(Cell[][] grid, int[] startPos, char targetEntranceCellBuildingChar) {
		final int startR = startPos[0];
		final int startC = startPos[1];
		final int rLen = grid.length;
		final int cLen = grid[0].length;
		final int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
		boolean[][] visited = new boolean[rLen][cLen];
		Queue<Pair<int[], List<Cell>>> q = new LinkedList<>();
		q.add(new Pair(new int[] {startR, startC}, new ArrayList<>(Arrays.asList(grid[startR][startC]))));
		visited[startR][startC] = true;
		while (!q.isEmpty()) {
			Pair<int[], List<Cell>> curr = q.poll();
			int[] pos = (int[]) curr.getKey();
			List<Cell> currList = (List<Cell>) curr.getValue();
			int r = pos[0];
			int c = pos[1];
			for (int[] dir : directions) {
				int nr = r + dir[0];
				int nc = c + dir[1];
				if (inBounds(grid, nr, nc) && !visited[nr][nc] &&
						(grid[nr][nc] instanceof PlayerCell || grid[nr][nc] instanceof FriendCell ||
						 grid[nr][nc] instanceof PathCell || grid[nr][nc] instanceof EntranceCell)) {
					boolean foundTargetEntranceCellBuildingChar = false;
					if (grid[nr][nc] instanceof EntranceCell &&
							((EntranceCell)grid[nr][nc]).getBuildingChar() == targetEntranceCellBuildingChar) {
						foundTargetEntranceCellBuildingChar = true;
					}
					List<Cell> nextList = new ArrayList<>();
					for (Cell cell : currList) {
						nextList.add(cell);
					}
					nextList.add(grid[nr][nc]);
					if (foundTargetEntranceCellBuildingChar) {
						return nextList;
					}
					visited[nr][nc] = true;
					q.add(new Pair(new int[] {nr, nc}, nextList));
				}
			}
		}
		return new ArrayList<>();
	}
	
	public List<Cell> displayShortestPath(int row, int col, char targetEntranceCellBuildingChar) {
		List<Cell> cellList = this.findShortestPath(grid, new int[] {row, col}, targetEntranceCellBuildingChar);
//		for (int i = 1; i < cellList.size(); i++) {
//			Cell cell = cellList.get(i);
//			cell.setColor(0, 255, 0);
//		}
		return cellList;
	}
	
	public void removeDisplayShortestPath() {
		for (Cell[] row : this.grid) {
			for (Cell cell : row) {
				cell.revertColorToOriginalColor();
			}
		}
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
		this.prevPlayerLocation[0] = this.playerLocation[0];
		this.prevPlayerLocation[1] = this.playerLocation[1];
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
