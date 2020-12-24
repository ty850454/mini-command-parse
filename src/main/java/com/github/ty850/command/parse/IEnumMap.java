package com.github.ty850.command.parse;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * EnumMap只能存枚举，这个类可以存实现了IEnum接口的枚举
 *
 * @author xy
 * @param <K> 枚举
 * @param <V> 枚举对应的值
 */
@SuppressWarnings("unused")
public class IEnumMap<K extends IEnum, V> implements Map<K, V> {

    /** key的类型 */
    private final Class<? extends K> keyType;

    /**
     * 所有key
     */
    private final transient K[] keys;

    /** value数组 */
    private final transient Object[] values;

    /** 有元素的数量 */
    private transient int size = 0;

    /** 空对象，用于区分数组中的null */
    private static final Object NULL = new Object() {
        /**
         * 返回哈希码
         *
         * @return 哈希码
         */
        @Override
        public int hashCode() {
            return 0;
        }

        /**
         * 返回字符串表现形式
         *
         * @return 字符串表现形式
         */
        @Override
        public String toString() {
            return "com.github.ty850454.command.parse.IEnumMap.NULL";
        }
    };

    public K[] keys() {
        return keys;
    }

    public IEnumMap(Class<? extends K> keyType) {
        this.keyType = keyType;
        keys = keyType.getEnumConstants();
        values = new Object[keys.length];
    }

    public IEnumMap(IEnumMap<K, ? extends V> m) {
        keyType = m.keyType;
        keys = m.keys;
        values = m.values.clone();
        size = m.size;
    }

    private Object maskNull(Object value) {
        return (value == null ? NULL : value);
    }

    private V unmaskNull(Object value) {
        // noinspection unchecked
        return (V)(value == NULL ? null : value);
    }

    private void typeCheck(K key) {
        Class<?> keyClass = key.getClass();
        if (keyClass != keyType && keyClass.getSuperclass() != keyType) {
            throw new ClassCastException(keyClass + " != " + keyType);
        }
    }

    private boolean isValidKey(Object key) {
        if (key == null) {
            return false;
        }
        Class<?> keyClass = key.getClass();
        return keyClass == keyType || keyClass.getSuperclass() == keyType;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return values.length == 0;
    }

    public boolean containsKey(Object key) {
        return isValidKey(key) && values[((Enum<?>)key).ordinal()] != null;
    }

    public boolean containsValue(Object value) {
        value = maskNull(value);
        for (Object val : values) {
            if (value.equals(val)) {
                return true;
            }
        }
        return false;
    }

    public V get(Object key) {
        return (isValidKey(key) ? unmaskNull(values[((Enum<?>)key).ordinal()]) : null);
    }

    public V put(K key, V value) {
        typeCheck(key);

        int index = key.ordinal();
        Object oldValue = values[index];
        values[index] = maskNull(value);
        if (oldValue == null) {
            size++;
        }
        return unmaskNull(oldValue);
    }

    public V remove(Object key) {
        if (!isValidKey(key)) {
            return null;
        }
        int index = ((Enum<?>)key).ordinal();
        Object oldValue = values[index];
        values[index] = null;
        if (oldValue != null) {
            size--;
        }
        return unmaskNull(oldValue);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        if (m instanceof IEnumMap) {
            IEnumMap<?, ?> em = (IEnumMap<?, ?>)m;
            if (em.keyType != keyType) {
                if (em.isEmpty()) {
                    return;
                }
                throw new ClassCastException(em.keyType + " != " + keyType);
            }

            for (int i = 0; i < keys.length; i++) {
                Object emValue = em.values[i];
                if (emValue != null) {
                    if (values[i] == null) {
                        size++;
                    }
                    values[i] = emValue;
                }
            }
        }
    }

    @Override
    public void clear() {
        Arrays.fill(values, null);
        size = 0;
    }

    private transient Set<K> keySet;

    @Override
    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new KeySet();
            keySet = ks;
        }
        return ks;
    }

    private class KeySet extends AbstractSet<K> {
        @Override
        public Iterator<K> iterator() {
            return new KeyIterator();
        }
        @Override
        public int size() {
            return size;
        }
        @Override
        public boolean contains(Object o) {
            return containsKey(o);
        }
        @Override
        public boolean remove(Object o) {
            int oldSize = size;
            IEnumMap.this.remove(o);
            return size != oldSize;
        }
        @Override
        public void clear() {
            IEnumMap.this.clear();
        }
    }

    private class KeyIterator extends BaseEnumMapIterator<K> {
        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturnedIndex = index++;
            return keys[lastReturnedIndex];
        }
    }

    private abstract class BaseEnumMapIterator<T> implements Iterator<T> {
        // Lower bound on index of next element to return
        int index = 0;

        // Index of last returned element, or -1 if none
        int lastReturnedIndex = -1;

        @Override
        public boolean hasNext() {
            while (index < values.length && values[index] == null) {
                index++;
            }
            return index != values.length;
        }

        @Override
        public void remove() {
            checkLastReturnedIndex();

            if (values[lastReturnedIndex] != null) {
                values[lastReturnedIndex] = null;
                size--;
            }
            lastReturnedIndex = -1;
        }

        private void checkLastReturnedIndex() {
            if (lastReturnedIndex < 0) {
                throw new IllegalStateException();
            }
        }
    }

    public Collection<V> values() {
        return null;
    }

    private transient Set<Map.Entry<K,V>> entrySet;

    public Set<Entry<K, V>> entrySet() {
        Set<Map.Entry<K,V>> es = entrySet;
        if (es != null) {
            return es;
        } else {
            return entrySet = new EntrySet();
        }
    }

    private int entryHashCode(int index) {
        return (keys[index].hashCode() ^ values[index].hashCode());
    }

    private boolean containsMapping(Object key, Object value) {
        return isValidKey(key) &&
                maskNull(value).equals(values[((Enum<?>)key).ordinal()]);
    }

    private boolean removeMapping(Object key, Object value) {
        if (!isValidKey(key)) {
            return false;
        }
        int index = ((Enum<?>)key).ordinal();
        if (maskNull(value).equals(values[index])) {
            values[index] = null;
            size--;
            return true;
        }
        return false;
    }

    private abstract class BaseEnumMapMapIterator<T> implements Iterator<T> {
        // Lower bound on index of next element to return
        int index = 0;

        // Index of last returned element, or -1 if none
        int lastReturnedIndex = -1;

        public boolean hasNext() {
            while (index < values.length && values[index] == null) {
                index++;
            }
            return index != values.length;
        }

        public void remove() {
            checkLastReturnedIndex();

            if (values[lastReturnedIndex] != null) {
                values[lastReturnedIndex] = null;
                size--;
            }
            lastReturnedIndex = -1;
        }

        private void checkLastReturnedIndex() {
            if (lastReturnedIndex < 0) {
                throw new IllegalStateException();
            }
        }
    }

    private class EntryIterator extends BaseEnumMapMapIterator<Entry<K,V>> {
        private EntryIterator.Entry lastReturnedEntry;

        public Map.Entry<K,V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturnedEntry = new EntryIterator.Entry(index++);
            return lastReturnedEntry;
        }

        @Override
        public void remove() {
            lastReturnedIndex = ((null == lastReturnedEntry) ? -1 : lastReturnedEntry.index);
            super.remove();
            lastReturnedEntry.index = lastReturnedIndex;
            lastReturnedEntry = null;
        }

        private class Entry implements Map.Entry<K,V> {
            private int index;

            private Entry(int index) {
                this.index = index;
            }

            public K getKey() {
                checkIndexForEntryUse();
                return keys[index];
            }

            public V getValue() {
                checkIndexForEntryUse();
                return unmaskNull(values[index]);
            }

            public V setValue(V value) {
                checkIndexForEntryUse();
                V oldValue = unmaskNull(values[index]);
                values[index] = maskNull(value);
                return oldValue;
            }

            @Override
            public boolean equals(Object o) {
                if (index < 0) {
                    return o == this;
                }

                if (!(o instanceof Map.Entry)) {
                    return false;
                }

                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                V ourValue = unmaskNull(values[index]);
                Object hisValue = e.getValue();
                return (e.getKey() == keys[index] &&
                        (ourValue == hisValue ||
                                (ourValue != null && ourValue.equals(hisValue))));
            }

            @Override
            public int hashCode() {
                if (index < 0) {
                    return super.hashCode();
                }
                return entryHashCode(index);
            }

            @Override
            public String toString() {
                if (index < 0) {
                    return super.toString();
                }
                return keys[index] + "=" + unmaskNull(values[index]);
            }

            private void checkIndexForEntryUse() {
                if (index < 0) {
                    throw new IllegalStateException("Entry was removed");
                }
            }
        }
    }

    private class EntrySet extends AbstractSet<Entry<K,V>> {
        @Override
        public Iterator<Entry<K,V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?,?> entry = (Map.Entry<?,?>)o;
            return containsMapping(entry.getKey(), entry.getValue());
        }
        @Override
        public boolean remove(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?,?> entry = (Map.Entry<?,?>)o;
            return removeMapping(entry.getKey(), entry.getValue());
        }
        @Override
        public int size() {
            return size;
        }

        @Override
        public void clear() {
            IEnumMap.this.clear();
        }

        @Override
        public Object[] toArray() {
            return fillEntryArray(new Object[size]);
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            int size = size();
            if (a.length < size) {
                a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
            }
            if (a.length > size) {
                a[size] = null;
            }
            return (T[]) fillEntryArray(a);
        }
        private Object[] fillEntryArray(Object[] a) {
            int j = 0;
            for (int i = 0; i < values.length; i++) {
                if (values[i] != null) {
                    a[j++] = new AbstractMap.SimpleEntry<>(keys[i], unmaskNull(values[i]));
                }
            }
            return a;
        }
    }

}
