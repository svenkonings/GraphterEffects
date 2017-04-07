package utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Class used for methods to manipulate String Objects and related tasks.
 */
public final class StringUtils {

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
     * Compare two Strings ending in integers with String first, Number second logic.
     *
     * @param id1 First String to be compared.
     * @param id2 Second String to be compared.
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
     * Splits a String into a String part at the beginning and an Integer part at the end.
     *
     * @param input Input to be splitted.
     * @return A Pair containing the two parts of the split.
     */
    public static Pair<String, Integer> stripTrailingNumbers(String input) {
        String digs = "";
        while (input.length() > 0 && (Character.isDigit(input.charAt(input.length() - 1)))) {
            digs = input.charAt(input.length() - 1) + digs;
            input = input.substring(0, input.length() - 1);
        }
        try {
            return new Pair<>(input, Integer.parseInt(digs));
        } catch (NumberFormatException e) {
            return new Pair<>(input, -1);
        }
    }

    /**
     * Returns an accurate String representation of any Object, including arrays and collections of arrays or arrays of
     * arrays.
     *
     * @param in Object of which the representation is required.
     * @return A String representing the Object.
     */
    public static String ObjectToString(Object in) {
        if (in instanceof Collection) {
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
        //throw new RuntimeException("String found without quotation marks!");
    }

    public static String removeQuotation(String in) {
        while (in.startsWith("'") && in.endsWith("'") || in.startsWith("\"") && in.endsWith("\"")) {
            in = in.substring(1, in.length() - 1);
        }
        return in;
    }

    public static boolean isInteger(String expectedValue) {
        try{
            Integer.parseInt(expectedValue);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(String expectedValue) {
        try{
            Double.parseDouble(expectedValue);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
