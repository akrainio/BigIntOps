package andreikrainiouk.bigint;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static andreikrainiouk.bigint.UBigInt.trim;
import static org.junit.Assert.assertEquals;

public class UBigIntTest {
    @Test
    public void testTrim() {
        List<Integer> test = new ArrayList<Integer>();
        test.add(0);
        trim(test);
        BigInt trimmed = new BigInt(test, true);
        assertEquals("0", trimmed.toString());
    }
}
