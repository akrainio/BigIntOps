package andreikrainiouk.bigint;

/**
 * Created by Andrei on 3/27/2016.
 */
public class IntHelper {
    //Used to find base^pow (does not support negative powers)
    public static int intPow (int base, int pow){
        assert (pow >= 0);
        int sum = 1;
        for (int i = 0; i < pow; i++) {
            sum = sum * base;
        }
        return sum;
    }

    //Used to determine number of digits in a given integer
    public static int numDigits (int value) {
        int i = 0;
        for (; value != 0; i++) {
            value /= 10;
        }
        return i;
    }

    //Used to determine the digit at a given location of an integer
    public static int getDigitAt (int value, int loc) {
        assert (loc >= 0);
        assert (loc < numDigits(value));
        return ((value % (intPow(10, (loc + 1)))) - (value % (intPow(10, loc))))/(intPow(10, loc));
    }
}
