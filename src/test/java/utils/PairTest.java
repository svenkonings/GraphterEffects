package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import utils.Pair;

public final class PairTest {


    @Test
    public void testPair() {
        Object a = new Object();
        Object b = new Object();
        assertNotEquals(a,b);
        Pair<Object, Object> pair = new Pair<>(a, b);
        assertEquals(a, pair.getFirst());
        assertEquals(b, pair.getSecond());
        assertEquals(a, pair.get(0));
        assertEquals(b, pair.get(1));
    }
}
