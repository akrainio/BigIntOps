package andreikrainiouk.bigint;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static andreikrainiouk.bigint.BigInt.trim;
import static org.junit.Assert.*;

public class BigIntTest {
    @Test
    public void testPrintBigInt() {
        BigInt num1 = new BigInt(1234);
        assertEquals("1234", num1.toString());
        BigInt num2 = new BigInt(-1234);
        assertEquals("-1234", num2.toString());
    }

    @Test
    public void testBigIntListConstructor() {
        List<Integer> testList = new ArrayList<Integer>();
        testList.add(0);
        testList.add(1);
        testList.add(2);
        testList.add(3);
        BigInt temp = new BigInt(testList, false);
        assertEquals("-3210", temp.toString());
        temp = new BigInt(testList, true);
        assertEquals("3210", temp.toString());
    }

    @Test
    public void testTrim() {
        List<Integer> test = new ArrayList<Integer>();
        test.add(0);
        trim(test);
        BigInt trimmed = new BigInt(test, true);
        assertEquals("0", trimmed.toString());
    }
    @Test
    public void testNegCopy() {
        BigInt num1 = new BigInt(-13);
        BigInt num2 = num1.negCopy();
        assertEquals("13", num2.toString());

        num1 = new BigInt(13);
        num2 = num1.negCopy();
        assertEquals("-13", num2.toString());
    }

    @Test
    public void testHigherMagnitude() {
        BigInt num1 = new BigInt(-10);
        BigInt num2 = new BigInt(10);
        assertTrue(!num1.higherMagnitude(num2));
        num1 = new BigInt(-11);
        num2 = new BigInt(10);
        assertTrue(num1.higherMagnitude(num2));
        num1 = new BigInt(-10);
        num2 = new BigInt(11);
        assertTrue(!num1.higherMagnitude(num2));
        num1 = new BigInt(10);
        num2 = new BigInt(11);
        assertTrue(!num1.higherMagnitude(num2));
        num1 = new BigInt(11);
        num2 = new BigInt(10);
        assertTrue(num1.higherMagnitude(num2));
    }

    @Test
    public void testCompareBigInt() {
        BigInt num1 = new BigInt(0);
        BigInt num2 = new BigInt(0);
        assertEquals(0, num1.compareBigInt(num2));
        num1 = new BigInt(1);
        num2 = new BigInt(0);
        assertEquals(1, num1.compareBigInt(num2));
        num1 = new BigInt(0);
        num2 = new BigInt(1);
        assertEquals(-1, num1.compareBigInt(num2));
        num1 = new BigInt(10);
        num2 = new BigInt(1);
        assertEquals(1, num1.compareBigInt(num2));
        num1 = new BigInt(1);
        num2 = new BigInt(10);
        assertEquals(-1, num1.compareBigInt(num2));
        num1 = new BigInt(-1);
        num2 = new BigInt(0);
        assertEquals(-1, num1.compareBigInt(num2));
        num1 = new BigInt(-1);
        num2 = new BigInt(-1);
        assertEquals(0, num1.compareBigInt(num2));
        num1 = new BigInt(-1);
        num2 = new BigInt(-10);
        assertEquals(1, num1.compareBigInt(num2));
        num1 = new BigInt(-10);
        num2 = new BigInt(-1);
        assertEquals(-1, num1.compareBigInt(num2));
        num1 = new BigInt(-10);
        num2 = new BigInt(10);
        assertEquals(-1, num1.compareBigInt(num2));
        num1 = new BigInt(10);
        num2 = new BigInt(-10);
        assertEquals(1, num1.compareBigInt(num2));
        num1 = new BigInt(-9);
        num2 = new BigInt(-8);
        assertEquals(-1, num1.compareBigInt(num2));
    }

    @Test
    public void testAdd(){
        BigInt num1 = new BigInt(10);
        BigInt num2 = new BigInt(20);
        BigInt num3 = num1.add(num2);
        assertEquals("30", num3.toString());
        num1 = new BigInt(99);
        num2 = new BigInt(1);
        num3 = num1.add(num2);
        assertEquals("100", num3.toString());
        num1 = new BigInt(9);
        num2 = new BigInt(8);
        num3 = num1.add(num2);
        assertEquals("17", num3.toString());
        num1 = new BigInt(-10);
        num2 = new BigInt(-20);
        num3 = num1.add(num2);
        assertEquals("-30", num3.toString());
        num1 = new BigInt(-99);
        num2 = new BigInt(-1);
        num3 = num1.add(num2);
        //assertEquals("-100", num3.toString());
        num1 = new BigInt(-9);
        num2 = new BigInt(-8);
        num3 = num1.add(num2);
        assertEquals("-17", num3.toString());
        num1 = new BigInt(0);
        num2 = new BigInt(0);
        num3 = num1.add(num2);
        assertEquals("0", num3.toString());
        //Adding negative numbers
        num1 = new BigInt(10);
        num2 = new BigInt(-20);
        num3 = num1.add(num2);
        assertEquals("-10", num3.toString());
        num1 = new BigInt(100);
        num2 = new BigInt(-1);
        num3 = num1.add(num2);
        assertEquals("99", num3.toString());
        num1 = new BigInt(9);
        num2 = new BigInt(-8);
        num3 = num1.add(num2);
        assertEquals("1", num3.toString());
        num1 = new BigInt(-10);
        num2 = new BigInt(20);
        num3 = num1.add(num2);
        assertEquals("10", num3.toString());
        num1 = new BigInt(-100);
        num2 = new BigInt(1);
        num3 = num1.add(num2);
        assertEquals("-99", num3.toString());
        num1 = new BigInt(-9);
        num2 = new BigInt(8);
        num3 = num1.add(num2);
        assertEquals("-1", num3.toString());
        num1 = new BigInt(0);
        num2 = new BigInt(5);
        num3 = num1.add(num2);
        assertEquals("5", num3.toString());
    }

    @Test
    public void testSub(){
        BigInt num1 = new BigInt(10);
        BigInt num2 = new BigInt(20);
        BigInt num3 = num1.sub(num2);
        assertEquals("-10", num3.toString());
        num1 = new BigInt(1);
        num2 = new BigInt(1);
        num3 = num1.sub(num2);
        assertEquals("0", num3.toString());
        num1 = new BigInt(9);
        num2 = new BigInt(8);
        num3 = num1.sub(num2);
        assertEquals("1", num3.toString());
        num1 = new BigInt(-10);
        num2 = new BigInt(-20);
        num3 = num1.sub(num2);
        assertEquals("10", num3.toString());
        num1 = new BigInt(-100);
        num2 = new BigInt(-1);
        num3 = num1.sub(num2);
        assertEquals("-99", num3.toString());
        num1 = new BigInt(8);
        num2 = new BigInt(-8);
        num3 = num1.sub(num2);
        assertEquals("16", num3.toString());
        num1 = new BigInt(0);
        num2 = new BigInt(5);
        num3 = num1.sub(num2);
        assertEquals("-5", num3.toString());
    }
}