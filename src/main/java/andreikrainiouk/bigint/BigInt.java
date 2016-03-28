package andreikrainiouk.bigint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 3/24/2016.
 */

public class BigInt {

    //ArrayList representation of a natural number
    private final List<Integer> value;
    //Denotes sign
    private final boolean positive;

    //Constructor function
    public BigInt(int value) {
        if (value < 0) {
            positive = false;
            this.value = toList(value * -1);
        } else {
            positive = true;
            this.value = toList(value);
        }
    }

    //Helper for constructor, converts positive integer into ArrayList
    private List<Integer> toList(int value) {
        List<Integer> newList = new ArrayList<Integer>();
        for (int i = 0; i < IntHelper.numDigits(value); i++) {
            newList.add(IntHelper.getDigitAt(value, i));
        }
        return newList;
    }

    //Prints out BigInt
    public void printBigInt() {
        if (!positive) System.out.print("-");
        for (int i = value.size(); i > 0; i--) {
            System.out.print(value.get(i - 1));
        }
        System.out.println();
    }

    //Compares 2 BigInts, returns -1 for this < that, 0 for this = that, and 1 for this > that, 2 for error
    public int compareBigInt(BigInt that) {
        if (this.positive && !that.positive) return 1;
        if (!this.positive && that.positive) return -1;
        if (this.positive && that.positive) {
            if (this.value.size() > that.value.size()) return 1;
            else if (this.value.size() < that.value.size()) return -1;
            else {
                for (int i = this.value.size() - 1; i >= 0; i++) {
                    if (this.value.get(i) > that.value.get(i)) return 1;
                    else if (this.value.get(i) < that.value.get(i)) return -1;
                }
                return 0;
            }
        }if (!this.positive && !that.positive) {
            if (this.value.size() < that.value.size()) return 1;
            else if (this.value.size() < that.value.size()) return -1;
            else {
                for (int i = this.value.size() - 1; i >= 0; i++) {
                    if (this.value.get(i) < that.value.get(i)) return 1;
                    else if (this.value.get(i) < that.value.get(i)) return -1;
                }
                return 0;
            }
        } else return 2;
    }

    //Externally called add function
    public BigInt add(BigInt that) {
        return that;
    }

    //Externally called subtraction function
    public BigInt subtract(BigInt that) {
        return that;
    }

    //Called by add and subtract, actual code that calculates sums
    private BigInt sum(BigInt that) {
        return that;
    }
}
