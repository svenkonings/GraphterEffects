package com.github.meteoorkip.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class StringUtils {

    /**
     * Returns whether this given {@code String} represents a double.
     *
     * @param input A given String.
     * @return Whether the {@code String} represents a double.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

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
     * Repeats a character n times
     *
     * @param in Character to be repeated.
     * @param n  Number of times to repeat.
     * @return String containing <code>in</code> n times.
     */
    public static String repeat(char in, int n) {
        char[] chars = new char[n];
        Arrays.fill(chars, in);
        return new String(chars);
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

    /**
     * Returns an accurate {@code String} representation of any {@code Object}, including arrays and collections of
     * arrays or arrays of
     * arrays.
     *
     * @param in Object of which the representation is required.
     * @return A {@code String} representing the Object.
     */
    public static String ObjectToString(Object in) {
        if (in instanceof Double && (Double) in == Math.floor((Double) in)) {
            return String.valueOf(((int) ((Double) in).doubleValue()));
        } else if (in instanceof Collection) {
            List<String> to = new LinkedList<>();
            for (Object i : ((Collection) in)) {
                to.add(ObjectToString(i));
            }
            return to.toString();
        } else if (in instanceof Object[]) {
            String[] res = new String[((Object[]) in).length];
            for (int i = 0; i < ((Object[]) in).length; i++) {
                res[i] = ObjectToString(((Object[]) in)[i]);
            }
            return Arrays.toString(res);
        } else if (in instanceof Integer || (in instanceof String && StringUtils.isInteger((String) in))) {
            return String.valueOf(in);
        } else if (in instanceof String && ((String) in).startsWith("\"")) {
            return (String) in;
        } else if (!(in instanceof String)) {
            return "\"" + removeQuotation(in.toString()) + "\"";
        }
        return String.valueOf(in);
    }


    /**
     * Returns whether this given {@code String} represents an integer.
     *
     * @param input A given String.
     * @return Whether the {@code String} represents an integer.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Ensures all Strings contained in this Object have quotes (") around them unless they represent integers.
     * Supported objects: array, {@link String}, {@link Collection}.
     *
     * @param in Input object.
     * @return The edited object.
     */
    public static Object enforceQuotesIfString(Object in) {
        if (in instanceof Object[]) {
            Object[] res = new String[((Object[]) in).length];
            for (int i = 0; i < ((Object[]) in).length; i++) {
                res[i] = enforceQuotesIfString(String.valueOf(((Object[]) in)[i]));
            }
            return res;
        } else if (in instanceof Collection) {
            List<Object> to = new LinkedList<>();
            for (Object i : ((Collection) in)) {
                to.add(enforceQuotesIfString(i));
            }
            return to;
        } else if (in instanceof String && isInteger((String) in)) {
            return in;
        } else if (in instanceof String) {
            if (!((String) in).startsWith("\"")) {
                in = "\"" + in;
            }
            if (!((String) in).endsWith("\"")) {
                in = in + "\"";
            }
            return in;
        }
        return in;
    }

    public static String Chomp(String content) {
        if (content.endsWith("\r\n")) {
            return content.substring(0, content.length()-2);
        }
        return content.endsWith("\r|\n") ? content.substring(0, content.length()-1) : content;
    }
}
