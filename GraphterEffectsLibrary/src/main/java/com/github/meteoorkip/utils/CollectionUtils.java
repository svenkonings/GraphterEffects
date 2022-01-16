package com.github.meteoorkip.utils;

import it.unibo.tuprolog.core.Atom;

import java.util.*;

public class CollectionUtils {

    public static <T> List<T> listOf(T... args) {
        return Arrays.asList(args);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2) {
        Map<K, V> res = mapOf(k1, v1);
        res.put(k2, v2);
        return res;
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1) {
        Map<K, V> res = mapOf();
        res.put(k1, v1);
        return res;
    }

    public static <K, V> Map<K, V> mapOf() {
        return new HashMap<>();
    }
}
