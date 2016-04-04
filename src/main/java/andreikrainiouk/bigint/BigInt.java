package andreikrainiouk.bigint;

import java.util.ArrayList;
import java.util.List;

class BigInt {

    //Fields////////////////////////////////////////////////////////////////////////////////////////

    //Contains value
    private final UBigInt value;
    //Denotes sign
    private final boolean positive;

    //Constructors//////////////////////////////////////////////////////////////////////////////////

    //Constructor from list
    BigInt(List<Integer> value, boolean positive) {
        this.positive = positive;
        this.value = new UBigInt(value);
    }

    BigInt(UBigInt value, boolean positive) {
        this.positive = positive;
        this.value = value;
    }

    //Constructor from int
    BigInt(int value) {
        if (value < 0) {
            positive = false;
            this.value = new UBigInt(value * -1);
        }
        else {
            positive = true;
            this.value = new UBigInt(value);
        }
    }

    //Private Methods///////////////////////////////////////////////////////////////////////////////


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
            return !this.higherMagnitude(that);
        }
        return this.higherMagnitude(that);
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
        if (this.equals(that)) return this.value.uAdd(this.value);
        //If |a| == |b|: 0
        if (this.equals(that.negCopy())) return new BigInt(0);
        //If both pos: a.uAdd(b)
        if (this.positive && that.positive) return this.value.uAdd(that.value);
        //if both neg: -|a|.uAdd(|b|)
        if (!this.positive && !that.positive) return this.value.uAdd(that.value).negCopy();
        //If (a) neg larger than (b) pos: -|a|.uSub(|b|)
        if (this.higherMagnitude(that) && !this.positive)
            return (this.value.uSub(that.value)).negCopy();
        //If (a) pos smaller than (b) neg: -|b|.uSub(|a|)
        if (that.higherMagnitude(this) && !that.positive)
            return (that.value.uSub(this.value)).negCopy();
        //If (a) pos larger than (b) neg: |a|.uSub(|b|)
        if (this.higherMagnitude(that) && this.positive)
            return this.value.uSub(that.value);
        //If (a) neg smaller than (b) pos: |b|.uSub(|a|)
        if (that.higherMagnitude(this) && that.positive)
            return that.value.uSub(this.value);
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
            return this.value.product(that.value);
        } return (this.value.product(that.value)).negCopy();
    }
}