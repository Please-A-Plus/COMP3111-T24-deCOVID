package comp3111.covid;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;


public class TwoWaysHashMap<K extends Object, V extends Object> {

    public Map<K,V> forward = new Hashtable<K, V>();
    public Map<V,K> backward = new Hashtable<V, K>();
  
    public synchronized void put(K key, V value) {
      forward.put(key, value);
      backward.put(value, key);
    }
  
    public synchronized V getForward(K key) {
      return forward.get(key);
    }
  
    public synchronized K getBackward(V key) {
      return backward.get(key);
    }

    public synchronized boolean containsKeyForward(K key) {
      return forward.containsKey(key);
    }
  
    public synchronized boolean containsKeyBackward(V key) {
      return backward.containsKey(key);
    }

    public synchronized Set<K> keySetForward() {
      return forward.keySet();
    }

    public synchronized Set<V> keySetBackward() {
      return backward.keySet();
    }

  }
  