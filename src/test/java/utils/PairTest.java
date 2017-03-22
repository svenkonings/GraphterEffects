package utils;

import static org.junit.Assert.*;
import org.junit.Test;
import utils.Pair;

/**
 * Created by poesd_000 on 22/03/2017.
 */
public class PairTest {


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
