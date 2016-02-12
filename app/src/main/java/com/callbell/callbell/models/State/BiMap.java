package com.callbell.callbell.models.State;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by austin on 1/23/16.
 */
public class BiMap<K,V> {
    private Map<K,V> byKeys;
    private Map<V, K> byValues;

    public BiMap() {
        byKeys = new HashMap<>();
        byValues = new HashMap<>();
    }

    public void put(K key, V value) {
        byKeys.put(key, value);
        byValues.put(value, key);
    }

    public V getValue(K key) {
        return byKeys.get(key);
    }

    public K getKey(V value) {
        return byValues.get(value);
    }
}
