import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private final WeightedQuickUnionUF quickUnionStructure;
	private final WeightedQuickUnionUF quickUnionStructureForIsFull;

	private final int gridSize;
	private final boolean[][] grid;

	private final int virtualTopSite;
	private final int virtualBottomSite;

	private int numOpenSites = 0;

	// create n-by-n grid, with all sites blocked
	public Percolation(int n) {

		if (n <= 0) {
			throw new IllegalArgumentException("N must be bigger than 0");
		}

		gridSize = n;
		grid = new boolean[n][n];

		quickUnionStructure = new WeightedQuickUnionUF(n * n + 2);
		quickUnionStructureForIsFull = new WeightedQuickUnionUF(n * n + 1);

		virtualTopSite = 0;
		virtualBottomSite = n * n + 1;
	}

	// open site (row, col) if it is not open already
	public void open(int row, int col) {

		if (row <= 0 || col <= 0 || row > gridSize || col > gridSize) {
			throw new IllegalArgumentException("Both arguments need to be bigger than 0 and smaller than n");
		}

		if (!isOpen(row, col)) {
			grid[row - 1][col - 1] = true;

			int fieldIndex = getFieldIndexInQuickUnionStructure(row, col);

			if (row == 1) {
				quickUnionStructure.union(virtualTopSite, fieldIndex);
				quickUnionStructureForIsFull.union(virtualTopSite, fieldIndex);
			}
			if (row == gridSize) {
				quickUnionStructure.union(virtualBottomSite, fieldIndex);
			}
			if (col != 1 && isOpen(row, col - 1)) {
				quickUnionStructure.union(getFieldIndexInQuickUnionStructure(row, col - 1), fieldIndex);
				quickUnionStructureForIsFull.union(getFieldIndexInQuickUnionStructure(row, col - 1), fieldIndex);
			}
			if (col != gridSize && isOpen(row, col + 1)) {
				quickUnionStructure.union(getFieldIndexInQuickUnionStructure(row, col + 1), fieldIndex);
				quickUnionStructureForIsFull.union(getFieldIndexInQuickUnionStructure(row, col + 1), fieldIndex);
			}
			if (row != 1 && isOpen(row - 1, col)) {
				quickUnionStructure.union(getFieldIndexInQuickUnionStructure(row - 1, col), fieldIndex);
				quickUnionStructureForIsFull.union(getFieldIndexInQuickUnionStructure(row - 1, col), fieldIndex);
			}
			if (row != gridSize && isOpen(row + 1, col)) {
				quickUnionStructure.union(getFieldIndexInQuickUnionStructure(row + 1, col), fieldIndex);
				quickUnionStructureForIsFull.union(getFieldIndexInQuickUnionStructure(row + 1, col), fieldIndex);
			}

			++numOpenSites;
		}
	}

	// is site (row, col) open?
	public boolean isOpen(int row, int col) {

		if (row <= 0 || col <= 0 || row > gridSize || col > gridSize) {
			throw new IllegalArgumentException("Both arguments need to be bigger than 0 and smaller than n");
		}

		return grid[row - 1][col - 1];
	}

	// is site (row, col) full?
	public boolean isFull(int row, int col) {

		if (row <= 0 || col <= 0 || row > gridSize || col > gridSize) {
			throw new IllegalArgumentException("Both arguments need to be bigger than 0 and smaller than n");
		}

		if (isOpen(row, col)) {
			int fieldIndex = getFieldIndexInQuickUnionStructure(row, col);
			return quickUnionStructureForIsFull.connected(virtualTopSite, fieldIndex);
		}
		return false;
	}

	// number of open sites
	public int numberOfOpenSites() {
		return numOpenSites;
	}

	// does the system percolate?
	public boolean percolates() {
		return quickUnionStructure.connected(virtualTopSite, virtualBottomSite);
	}

	private int getFieldIndexInQuickUnionStructure(int row, int col) {
		return (row - 1) * gridSize + col;
	}
}