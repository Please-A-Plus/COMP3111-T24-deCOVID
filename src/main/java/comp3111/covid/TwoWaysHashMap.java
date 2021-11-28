package comp3111.covid;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * A two-way hash map. This class implements a hashMap that can be seached from both ends.
 * 
 */
public class TwoWaysHashMap<K extends Object, V extends Object> {
    /**
     * The forward hash table.
     */
    public Map<K,V> forward = new Hashtable<K, V>();

    /**
     * The backward hash table.
     */
    public Map<V,K> backward = new Hashtable<V, K>();
  
    public synchronized void put(K key, V value) {
      forward.put(key, value);
      backward.put(value, key);
    }

    /**
     * get key from the forward hash table.
     * @param key the key to be searched.
     * @return the value associated with the key.
     */
    public synchronized V getForward(K key) {
      return forward.get(key);
    }
  
    /**
     * get key from the backward hash table.
     * @param key the key to be searched.
     * @return the value associated with the key.
     */
    public synchronized K getBackward(V key) {
      return backward.get(key);
    }

    /**
     * check if a forward hash table contains a key.
     * @param key the key to be checked.
     * @return true if the key is in the backward hash table.
     */
    public synchronized boolean containsKeyForward(K key) {
      return forward.containsKey(key);
    }
  
    /**
     * check if a backward hash table contains a key.
     * @param key the key to be checked.
     * @return true if the key is in the backward hash table.
     */
    public synchronized boolean containsKeyBackward(V key) {
      return backward.containsKey(key);
    }

    /**
     * get all the keys in the forward hash table.
     * @return a set of keys.
     */
    public synchronized Set<K> keySetForward() {
      return forward.keySet();
    }

    /**
     * get all the keys in the backward hash table.
     * @return a set of keys.
     */
    public synchronized Set<V> keySetBackward() {
      return backward.keySet();
    }

  }
  