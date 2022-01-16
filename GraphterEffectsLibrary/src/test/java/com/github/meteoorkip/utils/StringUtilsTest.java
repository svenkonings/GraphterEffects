package com.github.meteoorkip.utils;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class StringUtilsTest {

    @Test
    public void testStringUtils() {
        assertEquals("", StringUtils.repeat(' ', 0));
        assertEquals(" ", StringUtils.repeat(' ', 1));
        assertEquals("  ", StringUtils.repeat(' ', 2));
        assertEquals("cc", StringUtils.repeat('c', 2));
    }
}
