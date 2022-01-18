package com.github.meteoorkip.utils;

import java.util.*;

/**
 * Utility class that provides methods concerning collections of objects
 */
public class CollectionUtils {

    /**
     * Returns a list of the objects passed to the varargs parameters
     * @param args objects
     * @param <T> type of each object
     * @return a list of the objects
     */
    @SafeVarargs
    public static <T> List<T> listOf(T... args) {
        return Arrays.asList(args);
    }

    /**
     * Returns a map of the two key-value pairs passed as parameters
     * @param k1 first key
     * @param v1 first value
     * @param k2 second key
     * @param v2 second value
     * @param <K> class of all keys
     * @param <V> class of all values
     * @return a Map containing the two key-value pairs
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2) {
        Map<K, V> res = mapOf(k1, v1);
        res.put(k2, v2);
        return res;
    }

    /**
     * Returns a map of the key-value pair passed as parameters
     * @param k1 key
     * @param v1 value
     * @param <K> class of key
     * @param <V> class of value
     * @return a Map containing the key-value pair
     */
    public static <K, V> Map<K, V> mapOf(K k1, V v1) {
        Map<K, V> res = mapOf();
        res.put(k1, v1);
        return res;
    }

    /**
     * Returns a mutable empty Map
     * @param <K> class of keys that can be put in this map
     * @param <V> class of values that can be put in this map
     * @return empty Map
     */
    public static <K, V> Map<K, V> mapOf() {
        return new HashMap<>();
    }


}
