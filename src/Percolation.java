import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int size;
    private final boolean[][] grid;

    private final int virtualTopSite;
    private final int virtualBottomSite;
    private final WeightedQuickUnionUF unionFind;

    private final WeightedQuickUnionUF unionFindWithOnlyVirtualTop;

    private int openSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        size = n;
        grid = new boolean[n][n];
        openSites = 0;

        virtualTopSite = 0;
        virtualBottomSite = n * n + 1;
        unionFind = new WeightedQuickUnionUF(n * n + 2);

        unionFindWithOnlyVirtualTop = new WeightedQuickUnionUF(n * n + 1);
    }

    private int getUnionFindIndex(int row, int col) {
        return 1 + grid.length * (row - 1) + (col - 1);
    }

    public void open(int row, int col) {
        checkBounds(row);
        checkBounds(col);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSites += 1;
            connectToNeighbors(row, col);
        }
    }

    private void connectToNeighbors(int row, int col) {
        connectToNeighbor(row, col, row - 1, col);
        connectToNeighbor(row, col, row + 1, col);
        connectToNeighbor(row, col, row, col - 1);
        connectToNeighbor(row, col, row, col + 1);
        int unionFindIndex = getUnionFindIndex(row, col);
        if (row == 1) {
            unionFind.union(unionFindIndex, virtualTopSite);
            unionFindWithOnlyVirtualTop.union(unionFindIndex, virtualTopSite);
        }
        if (row == size) {
            unionFind.union(unionFindIndex, virtualBottomSite);
        }

    }

    private void connectToNeighbor(int row, int col, int neighborRow, int neighborCol) {
        if (isInBounds(neighborRow) && isInBounds(neighborCol) && isOpen(neighborRow, neighborCol)) {
            int unionFindIndex = getUnionFindIndex(row, col);
            int neighborUnionFindIndex = getUnionFindIndex(neighborRow, neighborCol);
            unionFind.union(unionFindIndex, neighborUnionFindIndex);
            unionFindWithOnlyVirtualTop.union(unionFindIndex, neighborUnionFindIndex);
        }
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean isOpen(int row, int col) {
        checkBounds(row);
        checkBounds(col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        checkBounds(row);
        checkBounds(col);
        return unionFindWithOnlyVirtualTop.connected(virtualTopSite, getUnionFindIndex(row, col));
    }

    private boolean isInBounds(int index) {
        return (index >= 1 && index <= size);
    }

    private void checkBounds(int index) {
        if (!isInBounds(index)) {
            throw new IllegalArgumentException();
        }
    }

    public boolean percolates() {
        return unionFind.connected(virtualTopSite, virtualBottomSite);
    }
}
