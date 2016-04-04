package andreikrainiouk.bigint;

class IntHelper {
    //Used to find base^pow (does not support negative powers)
    static int intPow(int base, int pow){
        assert (pow >= 0);
        int sum = 1;
        for (int i = 0; i < pow; i++) {
            sum = sum * base;
        }
        return sum;
    }

    //Used to determine number of digits in a given integer
    static int numDigits(int value) {
        if (value == 0) return 1;
        int i = 0;
        for (; value != 0; i++) {
            value /= 10;
        }
        return i;
    }

    //Used to determine the digit at a given location of an integer
    static int getDigitAt(int value, int loc) {
        //c++ code: d1 = i < v.size() ? v[i] : 0;
        assert (loc >= 0);
        assert (loc < numDigits(value));
        return ((value % (intPow(10, (loc + 1)))) - (value % (intPow(10, loc))))/(intPow(10, loc));
    }
}
