package andreikrainiouk.bigint;

import org.junit.Test;
import static org.junit.Assert.*;
import static andreikrainiouk.bigint.IntHelper.*;

/**
 * Created by Andrei on 3/27/2016.
 */

public class IntHelperTest {
    @Test
    public void testIntPow() {
        assertEquals(1, intPow(10, 0));

        assertEquals(10, intPow(10, 1));
        assertEquals(100, intPow(10, 2));
        assertEquals(0, intPow(0, 1));
        assertEquals(1, intPow(0, 0));
        assertEquals(1, intPow(-1, 0));
        assertEquals (-1, intPow(-1, 1));
        assertEquals (1, intPow(-1, 2));
        assertEquals(-1, intPow(-1, 3));
        assertEquals(9, intPow(-3, 2));
    }

    @Test
    public void testNumDigits() {
        assertEquals(0, numDigits(0));
        assertEquals(1, numDigits(1));
        assertEquals(2, numDigits(21));
        assertEquals(3, numDigits(333));
        assertEquals(4, numDigits(4141));
    }

    @Test
    public void testGetDigitAt() {
        assertEquals(0, getDigitAt(543210, 0));
        assertEquals(1, getDigitAt(543210, 1));
        assertEquals(2, getDigitAt(543210, 2));
        assertEquals(3, getDigitAt(543210, 3));
        assertEquals(4, getDigitAt(543210, 4));
        assertEquals(5, getDigitAt(543210, 5));
    }
}