package utils;

import java.util.Arrays;

/**
 * Created by poesd_000 on 22/03/2017.
 */
public class StringUtils {

    public static String repeat(char in, int n) {
        char[] chars = new char[n];
        Arrays.fill(chars, in);
        return new String(chars);
    }
}
