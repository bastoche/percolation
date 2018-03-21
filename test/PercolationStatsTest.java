import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercolationStatsTest {

    @Test
    void constructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PercolationStats(0, 1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new PercolationStats(1, 0);
        });
    }
}