package andreikrainiouk.bigint;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BigIntTest {
    @Test
    public void testPrintBigInt() {
        System.out.println("Should print out '1234'");
        BigInt num1 = new BigInt(1234);
        num1.printBigInt();
        System.out.println("Should print out '-1234'");
        BigInt num2 = new BigInt(-1234);
        num2.printBigInt();
    }

    @Test
    public void testBigIntListConstructor() {
        List<Integer> testList = new ArrayList<Integer>();
        testList.add(0);
        testList.add(1);
        testList.add(2);
        testList.add(3);
        BigInt temp = new BigInt(testList, false);
        System.out.println("Should print '-3210'");
        temp.printBigInt();
        temp = new BigInt(testList, true);
        System.out.println("Should print '3210'");
        temp.printBigInt();
    }

    @Test
    public void testNegCopy() {
        BigInt num1 = new BigInt(-13);
        BigInt num2 = num1.negCopy();
        num1.printBigInt();
        System.out.println("multiplied by -1 is:");
        num2.printBigInt();
        System.out.println();
        num1 = new BigInt(13);
        num2 = num1.negCopy();
        num1.printBigInt();
        System.out.println("multiplied by -1 is:");
        num2.printBigInt();
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
    }

    @Test
    public void testAdd(){
        BigInt num1 = new BigInt(10);
        BigInt num2 = new BigInt(20);
        BigInt num3 = num1.add(num2);
        System.out.println("Should be '30'");
        num3.printBigInt();
        num1 = new BigInt(99);
        num2 = new BigInt(1);
        num3 = num1.add(num2);
        System.out.println("Should be '100'");
        num3.printBigInt();
        num1 = new BigInt(9);
        num2 = new BigInt(8);
        num3 = num1.add(num2);
        System.out.println("Should be '17'");
        num3.printBigInt();
        num1 = new BigInt(-10);
        num2 = new BigInt(-20);
        num3 = num1.add(num2);
        System.out.println("Should be '-30'");
        num3.printBigInt();
        num1 = new BigInt(-99);
        num2 = new BigInt(-1);
        num3 = num1.add(num2);
        System.out.println("Should be '-100'");
        num3.printBigInt();
        num1 = new BigInt(-9);
        num2 = new BigInt(-8);
        num3 = num1.add(num2);
        System.out.println("Should be '-17'");
        num3.printBigInt();
    }
}