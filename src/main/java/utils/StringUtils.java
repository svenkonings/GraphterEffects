package utils;

import java.util.Arrays;

public final class StringUtils {

    public static String repeat(char in, int n) {
        char[] chars = new char[n];
        Arrays.fill(chars, in);
        return new String(chars);
    }

    public static int compareStrings(String id1, String id2) {
        Pair<String, Integer> stripped1 = stripTrailingNumbers(id1);
        Pair<String, Integer> stripped2 = stripTrailingNumbers(id2);
        if (!stripped1.getFirst().equals(stripped2.getFirst())) {
            return id1.compareTo(id1);
        } else {
            return Integer.compare(stripped1.getSecond(), stripped2.getSecond());
        }
    }

    public static Pair<String, Integer> stripTrailingNumbers(String input) {
        String digs = "";
        while (input.length() > 0 && (Character.isDigit(input.charAt(input.length()-1)))) {
            digs = input.charAt(input.length()-1) + digs;
            input = input.substring(0, input.length()-1);
        }
        try {
            return new Pair<>(input, Integer.parseInt(digs));
        } catch (NumberFormatException e) {
            return new Pair<>(input, -1);
        }
    }
}
