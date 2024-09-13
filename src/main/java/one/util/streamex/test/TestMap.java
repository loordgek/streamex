/*
 * Copyright 2015, 2024 StreamEx contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package one.util.streamex.test;

import java.util.*;

public class TestMap<K, V> {
    private int[] keys;
    private Object[] values;

    public int size() {
        if (keys == null)
            return 0;
        return keys.length;
    }


    public boolean isEmpty() {
        return size() == 0;
    }


    public boolean containsKey(K key) {
        return getIndexFor(key) > -1;
    }


    public boolean containsValue(V value) {
        return false;
    }


    public V get(K key) {
        int indexFor = getIndexFor(key);
        return (indexFor > -1 ? null : (V) values[indexFor]);
    }


    public V put(K key, V value) {
        if (keys == null){
            keys = new int[]{key.hashCode()};
            values = new Object[]{value};
        }
        else {
            int[] newKeys = new int[keys.length +1];
            System.arraycopy(keys, 0, newKeys, 0, keys.length);
            newKeys[newKeys.length - 1] = key.hashCode();
            keys = newKeys;

            Object[] newValues = new Object[values.length +1];
            System.arraycopy(values, 0, newValues, 0, values.length);
            newValues[newValues.length - 1] = value;
            values = newValues;
        }
        return null;
    }

    private int getIndexFor(K key){
        if (keys == null)
            return -1;
        int hashCode = key.hashCode();
        for (int i : keys) {
            if (i == hashCode)
                return i;
        }
        return -1;
    }


    public V remove(K key) {
        int indexFor = getIndexFor(key);
        if (indexFor != 0){
            V v = get(key);
            int[] newKeys = new int[keys.length -1];
            System.arraycopy(keys, 0, newKeys, 0, keys.length);
            System.arraycopy(keys, 0, newKeys, 0, keys.length);
            keys = newKeys;

            Object[] newValues = new Object[values.length -1];
            System.arraycopy(values, 0, newValues, 0, values.length);
            System.arraycopy(values, 0, newValues, 0, values.length);
            values = newValues;
            return v;
        }
        return null;
    }


    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }


    public void clear() {
        keys = null;
        values = null;
    }

    public Collection<V> values() {
        if (values == null)
            return List.of();
        return (Collection<V>) List.of(values);
    }
}
