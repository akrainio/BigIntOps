package andreikrainiouk.bigint;

import java.util.ArrayList;
import java.util.List;

class BigInt {

    //Fields////////////////////////////////////////////////////////////////////////////////////////

    //ArrayList representation of a natural number
    private final List<Integer> value;

    //Denotes sign
    private final boolean positive;

    //Constructors//////////////////////////////////////////////////////////////////////////////////

    //Constructor from list
    BigInt(List<Integer> value, boolean positive) {
        this.positive = positive;
        this.value = value;
    }

    //Constructor from int
    BigInt(int value) {
        if (value < 0) {
            positive = false;
            this.value = toList(value * -1);
        } else {
            positive = true;
            this.value = toList(value);
        }
    }

    //Private Methods///////////////////////////////////////////////////////////////////////////////

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
    private BigInt uAdd(BigInt that) {
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
    private BigInt uSub(BigInt that) {
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
    private BigInt product(BigInt that) {
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
    //Package private

    //Creates copy of given BigInt with opposite sign
    BigInt negCopy() {
        return new BigInt(this.value, !this.positive);
    }

    //Public Methods////////////////////////////////////////////////////////////////////////////////

    //Tests expression (this < that)
    boolean less(BigInt that) {
        if (this.positive && !that.positive) return false;
        if (!this.positive && that.positive) return true;
        if (this.positive) {
            if (this.higherMagnitude(that)) return false;
            else return true;
        }
        if (this.higherMagnitude(that)) return true;
        else return false;
    }

    //Tests expression (this == that)
    boolean equals(BigInt that) {
        return (this.positive == that.positive) && (!this.higherMagnitude(that) && !that.higherMagnitude(this));
    }

    //Tests expression (|this| > |that|)
    boolean higherMagnitude(BigInt that) {
        if (this.value.size() > that.value.size()) return true;
        else if (this.value.size() < that.value.size()) return false;
        else {
            //Digit by digit comparison
            for (int i = this.value.size() - 1; i >= 0; --i) {
                if (this.value.get(i) > that.value.get(i)) return true;
                else if (this.value.get(i) < that.value.get(i)) return false;
            }
            return false;
        }
    }

    //Prints out BigInt
    public String toString() {
        if (value.isEmpty()) return "0";
        StringBuilder output = new StringBuilder();
        if (!positive) output.append("-");
        for (int i = value.size(); i > 0; i--) {
            output.append(value.get(i - 1));
        }
        return output.toString();
    }

    //Externally called add method, runs sum method for largerInput.sum(smallerInput), compares
    //digit length first
    BigInt add(BigInt that) {
        //If a == b: a.multiply(2)
        if (this.equals(that)) return this.uAdd(this);
        //If |a| == |b|: 0
        if (this.equals(that.negCopy())) return new BigInt(0);
        //If both pos: a.uAdd(b)
        if (this.positive && that.positive) return this.uAdd(that);
        //if both neg: -|a|.uAdd(|b|)
        if (!this.positive && !that.positive) return this.uAdd(that).negCopy();
        //If (a) neg larger than (b) pos: -|a|.uSub(|b|)
        if (this.higherMagnitude(that) && !this.positive) return (this.uSub(that)).negCopy();
        //If (a) pos smaller than (b) neg: -|b|.uSub(|a|)
        if (that.higherMagnitude(this) && !that.positive) return (that.uSub(this)).negCopy();
        //If (a) pos larger than (b) neg: |a|.uSub(|b|)
        if (this.higherMagnitude(that) && this.positive) return this.uSub(that);
        //If (a) neg smaller than (b) pos: |b|.uSub(|a|)
        if (that.higherMagnitude(this) && that.positive) return that.uSub(this);
        else {
            System.err.println("BigInt.add():: Bad code");
            return new BigInt(0);
        }
    }

    //Externally called subtraction method operating on an int and a BigInt
    BigInt sub(BigInt that) {
        return this.add(that.negCopy());
    }

    //Externally called multiplication method
    public BigInt multiply(int factor) {
        return this.multiply(new BigInt(factor));
    }

    //Externally called multiplication method operating on two BigInts
    public BigInt multiply(BigInt that) {
        if ((this.value.size() == 1 && that.value.get(0) == 0) ||
                (that.value.size() == 1 && that.value.get(0) == 0)) {
            return new BigInt(0);
        }
        if (this.positive == that.positive) {
            //Same sign multiplication
            return this.product(that);
        } return (this.product(that)).negCopy();
    }
}