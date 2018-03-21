import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercolationTest {

    @Test
    void constructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Percolation(0);
        });
    }

    @Test
    void numberOfOpenSites_noOpenSites() {
        Percolation percolation = new Percolation(10);
        assertEquals(0, percolation.numberOfOpenSites());
    }

    @Test
    void numberOfOpenSites_oneOpenSite() {
        Percolation percolation = new Percolation(10);
        percolation.open(1, 2);
        assertEquals(1, percolation.numberOfOpenSites());
    }

    @Test
    void numberOfOpenSites_openSameSiteTwice() {
        Percolation percolation = new Percolation(10);
        percolation.open(1, 2);
        percolation.open(1, 2);
        assertEquals(1, percolation.numberOfOpenSites());
    }

    @Test
    void open_outOfBounds() {
        Percolation percolation = new Percolation(10);
        assertThrows(IllegalArgumentException.class, () -> {
            percolation.open(-1, -1);
        });
    }


    @Test
    void isOpen_open() {
        Percolation percolation = new Percolation(10);
        percolation.open(1, 2);
        assertTrue(percolation.isOpen(1, 2));
    }

    @Test
    void isOpen_blocked() {
        Percolation percolation = new Percolation(10);
        assertFalse(percolation.isOpen(1, 2));
    }

    @Test
    void isOpen_outOfBounds() {
        Percolation percolation = new Percolation(10);
        assertThrows(IllegalArgumentException.class, () -> {
            percolation.isOpen(-1, -1);
        });
    }

    @Test
    void isFull_false() {
        Percolation percolation = new Percolation(10);
        assertFalse(percolation.isFull(1, 2));
    }

    @Test
    void isFull_true() {
        Percolation percolation = new Percolation(10);
        percolation.open(1, 1);
        percolation.open(2, 1);
        assertTrue(percolation.isFull(2, 1));
    }

    @Test
    void isFull_outOfBounds() {
        Percolation percolation = new Percolation(10);
        assertThrows(IllegalArgumentException.class, () -> {
            percolation.isFull(-1, -1);
        });
    }

    @Test
    void percolates_false() {
        Percolation percolation = new Percolation(10);
        assertFalse(percolation.percolates());
    }

    @Test
    void percolates_true() {
        Percolation percolation = new Percolation(3);
        percolation.open(1,1);
        percolation.open(2,1);
        percolation.open(3,1);
        assertTrue(percolation.percolates());
    }

    @Test
    void backwash() {
        Percolation percolation = new Percolation(3);
        percolation.open(1,1);
        percolation.open(2,1);
        percolation.open(3,1);
        percolation.open(3,3);
        assertFalse(percolation.isFull(3, 3));
    }
}