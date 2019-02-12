package com.company;

import java.util.LinkedList;

public class Hash<K, V> implements HashI<K,V> {

    class HashElement<K, V> implements Comparable<HashElement<K,V>> {

        K key;
        V value;

        public HashElement(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(HashElement<K, V> o) {
            return (((Comparable<K>) this.key).compareTo(o.key));
        }

    }

    K key;
    V value;
    int numElements;
    int tableSize;
    double maxLoadFactor;
    LinkedList<HashElement<K,V>>[] hashArr;

    public Hash(int tableSize) {
        this.tableSize = tableSize;
        this.numElements = 0;
        this.maxLoadFactor = 0.75;

        hashArr = (LinkedList<HashElement<K,V>>[]) new LinkedList[tableSize];

        for (int i = 0; i < tableSize; i++)
            hashArr[i] = new LinkedList<>();
    }

    public void put(K key, V value) {
        if (maxLoadFactor < (numElements / tableSize))
            resize(tableSize * 2);

        HashElement<K,V> elem = new HashElement<>(key, value);

        int hashValue = (elem.key.hashCode() & 0x7fffffff) % tableSize;
        hashArr[hashValue].add(elem);

        numElements++;
    }

    public void remove(K key, V value) {
        HashElement<K,V> elem = new HashElement<>(key, value);

        int hashValue = (elem.key.hashCode() & 0x7fffffff) % tableSize;
        hashArr[hashValue].remove(elem);

        numElements--;
    }

    public void resize(int newSize) {
        LinkedList<HashElement<K, V>>[] newList = (LinkedList<HashElement<K, V>>[]) new LinkedList[newSize];
        for (int i = 0; i < newSize; i++) {
            newList[i] = new LinkedList<HashElement<K, V>>();
        }
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }
}
