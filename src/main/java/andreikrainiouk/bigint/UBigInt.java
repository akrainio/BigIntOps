package andreikrainiouk.bigint;

import java.util.ArrayList;
import java.util.List;

public class UBigInt {

    //Fields////////////////////////////////////////////////////////////////////////////////////////

    //ArrayList representation of a natural number
    private final List<Integer> value;

    //Constructors//////////////////////////////////////////////////////////////////////////////////
    UBigInt (int value) {
        this.value = toList(value);
    }

    UBigInt (List<Integer> value) {
        this.value = value;
    }

    //Helper for constructor, converts positive integer into ArrayList
    private List<Integer> toList(int value) {
        List<Integer> newList = new ArrayList<Integer>();
        for (int i = 0; i < IntHelper.numDigits(value); i++) {
            newList.add(IntHelper.getDigitAt(value, i));
        }
        return newList;
    }

    //Removes leading zeroes from ArrayList
    static void trim(List<Integer> value) {
        while (!value.isEmpty()&& value.get(value.size() - 1) == 0 && value.size() != 1) {
            value.remove(value.size() - 1);
        }
    }

    //Called by add, calculates sum of two positive BigInts, where the first has a
    //higher magnitude than the second
    BigInt uAdd(UBigInt that) {
        int carry = 0;
        List<Integer> total = new ArrayList<Integer>();
        for (int i = 0; i < this.value.size(); i++){
            int sum = this.value.get(i) + carry;
            if (i < that.value.size()) sum += that.value.get(i);
            total.add(sum % 10);
            carry = sum / 10;
        }
        if (carry != 0) {
            assert (carry > 0);
            assert (carry < 10);
            total.add(carry);
        }
        return new BigInt(total, true);
    }

    //Called by add, calculates difference between two positive BigInts, where the first has
    //a higher magnitude than the second
    BigInt uSub(UBigInt that) {
        List<Integer> total = new ArrayList<Integer>();
        int carry = 0;
        for (int i = 0; i < this.value.size(); ++i) {
            int sum = this.value.get(i) + 10 - carry;
            if (i < that.value.size()) sum -= that.value.get(i);
            if (sum < 10) carry = 1;
            else carry = 0;
            sum = sum % 10;
            total.add(sum);
        }
        trim(total);
        return new BigInt(total, true);
    }

    //Called by multiply, computes product of two positive BigInts
    BigInt product(UBigInt that) {
        List<Integer> p = new ArrayList<Integer>();
        //Initialize ArrayList of size m + n to 0;
        for (int i = 0; i < this.value.size() + that.value.size(); ++i) {
            p.add(0);
        }
        for (int i = 0; i < this.value.size(); ++i) {
            int c = 0;
            for (int j = 0; j < that.value.size(); ++j) {
                int d = p.get(i + j) + this.value.get(i) * that.value.get(j) + c;
                p.set(i + j, d % 10);
                c = d / 10;
            }
            p.set(i + that.value.size(), c);
        }
        trim(p);
        return new BigInt(p, true);
    }

    //Returns size of the ArrayList
    int size() {
        return value.size();
    }

    //Returns element at index
    int get(int i) {
        assert (i < size());
        return value.get(i);
    }

    //Returns true if ArrayList is empty
    boolean isEmpty() {
        return value.isEmpty();
    }
}
