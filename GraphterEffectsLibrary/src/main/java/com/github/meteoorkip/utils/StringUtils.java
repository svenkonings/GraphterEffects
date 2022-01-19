package com.github.meteoorkip.utils;

import java.math.BigDecimal;

public class StringUtils {

    /**
     * Parses a String into an integer.
     * The difference between this and {@link Integer#parseInt} is that his parses a decimal dot as well.
     * A {@link NumberFormatException} is thrown when the String could not be parsed.
     *
     * @param value A given {@code String}.
     * @return The parsed int.
     */
    public static int parseInt(String value) {
        return new BigDecimal(value).intValue();
    }

    /**
     * Splits a {@code String} into a {@code String} part at the beginning and an Integer part at the end.
     *
     * @param input Input to be splitted.
     * @return A {@link Pair} containing the two parts of the split.
     */
    public static Pair<String, Integer> stripTrailingNumbers(String input) {
        StringBuilder digs = new StringBuilder();
        while (input.length() > 0 && (Character.isDigit(input.charAt(input.length() - 1)))) {
            digs.insert(0, input.charAt(input.length() - 1));
            input = input.substring(0, input.length() - 1);
        }
        try {
            return new Pair<>(input, Integer.parseInt(digs.toString()));
        } catch (NumberFormatException e) {
            return new Pair<>(input, -1);
        }
    }


    /**
     * Compare two {@code String} objects ending in integers with {@code String} first, number second logic.
     *
     * @param id1 First {@code String} to be compared.
     * @param id2 Second {@code String} to be compared.
     * @return -1, 0 or 1 depending on the result of the comparison.
     */
    public static int compareStrings(String id1, String id2) {
        Pair<String, Integer> stripped1 = stripTrailingNumbers(id1);
        Pair<String, Integer> stripped2 = stripTrailingNumbers(id2);
        if (!stripped1.getFirst().equals(stripped2.getFirst())) {
            return id1.compareTo(id2);
        } else {
            return Integer.compare(stripped1.getSecond(), stripped2.getSecond());
        }
    }


    /**
     * Removes all surrounding quotation from a given {@code String}
     *
     * @param in A given String
     * @return A Sring with all surrounding quotation removed.
     */
    public static String removeQuotation(String in) {
        while (in.startsWith("'") && in.endsWith("'") || in.startsWith("\"") && in.endsWith("\"")) {
            in = in.substring(1, in.length() - 1);
        }
        return in;
    }

    public static String chomp(String content) {
        if (content.endsWith("\r\n")) {
            return content.substring(0, content.length()-2);
        }
        return content.endsWith("\r|\n") ? content.substring(0, content.length()-1) : content;
    }
}
