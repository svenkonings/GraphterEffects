package utils;

import org.junit.Test;
import utils.StringUtils;

import static org.junit.Assert.*;


public final class StringUtilsTest {

    @Test
    public void testStringUtils() {
        assertEquals("", StringUtils.repeat(' ', 0));
        assertEquals(" ", StringUtils.repeat(' ', 1));
        assertEquals("  ", StringUtils.repeat(' ', 2));
        assertEquals("cc", StringUtils.repeat('c', 2));
    }
}
