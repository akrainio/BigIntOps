package andreikrainiouk.bigint;

import java.util.ArrayList;
import java.util.List;

public class BigInt {

    //Fields////////////////////////////////////////////////////////////////////////////////////////

    //ArrayList representation of a natural number
    private final List<Integer> value;
    //Denotes sign


    private final boolean positive;

    //Constructors//////////////////////////////////////////////////////////////////////////////////

    //Constructor from list
    public BigInt(List<Integer> value, boolean positive) {
        if (!positive) {
            this.positive = false;
        } else {
            this.positive = true;
        }
        this.value = value;
    }

    //Constructor from int
    public BigInt(int value) {
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
    private void trim(List<Integer> value) {
        while (true) {
            if (value.get(value.size() - 1) == 0) value.remove(value.size() - 1);
            else break;
        }
    }

    //Called by add, calculates sum of two positive BigInts, where the first has a
    //higher magnitude than the second
    private BigInt uAdd(BigInt that) {
        int carry = 0;
        List<Integer> total = new ArrayList<Integer>();
        for (int i = 0; i < this.value.size(); i++){
            //When processing digits only in this
            if (i >= that.value.size()) {
                int sum = this.value.get(i) + carry;
                total.add(sum % 10);
                carry = sum / 10;
            //When processing digits existing in both this and that
            } else {
                int sum = this.value.get(i) + that.value.get(i) + carry;
                total.add(sum % 10);
                carry = sum / 10;
            }
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

    //Package private

    //Creates copy of given BigInt with opposite sign
    BigInt negCopy() {
        int thisValue = 0;
        for (int i = 0; i < this.value.size(); i++) {
            thisValue += this.value.get(i) * IntHelper.intPow(10, i);
        }
        if (this.positive) return new BigInt(thisValue * -1);
        else return new BigInt(thisValue);
    }

    //Public Methods////////////////////////////////////////////////////////////////////////////////

    //Compares 2 BigInts, returns -1 for this < that, 0 for this = that, and 1 for this > that,
    //and 2 for error
    public int compareBigInt(BigInt that) {
        //only needs less than and equals to operators for C++ implementation
        //Cases for opposite signs
        if (this.positive && !that.positive) return 1;
        if (!this.positive && that.positive) return -1;
        //Cases for positive signs
        if (this.positive && that.positive) {
            //Cases of differing number of digits
            if (this.value.size() > that.value.size()) return 1;
            else if (this.value.size() < that.value.size()) return -1;
            else {
                //Digit by digit comparison
                for (int i = this.value.size() - 1; i >= 0; --i) {
                    if (this.value.get(i) > that.value.get(i)) return 1;
                    else if (this.value.get(i) < that.value.get(i)) return -1;
                }
                return 0;
            }
            //Cases for negative signs
        }if (!this.positive && !that.positive) {
            //Cases of differing number of digits
            if (this.value.size() < that.value.size()) return 1;
            else if (this.value.size() > that.value.size()) return -1;
            else {
                //Digit by digit comparison
                for (int i = this.value.size() - 1; i >= 0; i--) {
                    if (this.value.get(i) < that.value.get(i)) return 1;
                    else if (this.value.get(i) > that.value.get(i)) return -1;
                }
                return 0;
            }
        } else return 2;
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
    public BigInt add(BigInt that) {
        //If a == b: a.multiply(2)
        if (this.compareBigInt(that) == 0) return this.uAdd(this);
        //If |a| == |b|: 0
        if (this.compareBigInt(that.negCopy()) == 0) return new BigInt(0);
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
    public BigInt sub(BigInt that) {
        return this.add(that.negCopy());
    }

    //Externally called multiplication method
    public BigInt multiply(int factor) {
        return this;
    }

    //Externally called multiplication method operating on two BigInts
    public BigInt multiply(BigInt that) {
        return this;
    }
}