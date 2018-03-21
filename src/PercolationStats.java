import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_FACTOR = 1.96;

    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        double[] percolationThresholds = new double[trials];
        for (int trial = 0; trial < trials; trial++) {
            percolationThresholds[trial] = runTrial(n);
        }

        mean = StdStats.mean(percolationThresholds);
        stddev = StdStats.stddev(percolationThresholds);
        confidenceLo = mean - (CONFIDENCE_FACTOR * stddev / Math.sqrt(trials));
        confidenceHi = mean + (CONFIDENCE_FACTOR * stddev / Math.sqrt(trials));
    }

    private static double runTrial(int n) {
        Percolation percolation = new Percolation(n);

        while (!percolation.percolates()) {
            int randomRow = StdRandom.uniform(1, n + 1);
            int randomCol = StdRandom.uniform(1, n + 1);
            if (!percolation.isOpen(randomRow, randomCol)) {
                percolation.open(randomRow, randomCol);
            }
        }

        return ((double) percolation.numberOfOpenSites()) / ((double) (n * n));
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confidenceLo;
    }

    public double confidenceHi() {
        return confidenceHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.println(percolationStats.mean());
        StdOut.println(percolationStats.stddev());
        StdOut.println(percolationStats.confidenceLo());
        StdOut.println(percolationStats.confidenceHi());
    }

}
