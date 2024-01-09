import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NumericRangeToolsTest {
    @Test
    public void TestPercentageValid() {
        int percentage = 0; // aanpassen met implementatie

        assertTrue(percentage >= 0 && percentage <= 100);
    }

    @Test
    public void testPercentageInvalidOutOfRangeLow() {
        int percentage = -5; // aanpassen met implementatie

        assertFalse(percentage < 0);
        
    }

    @Test
    public void testPercentageInvalidOutOfRangeHigh() {
        int percentage = 110;

        assertFalse(percentage > 100);
    }
}
