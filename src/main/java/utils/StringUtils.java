package utils;

import java.util.Arrays;

public final class StringUtils {

    public static String repeat(char in, int n) {
        char[] chars = new char[n];
        Arrays.fill(chars, in);
        return new String(chars);
    }
}
