
# Java Fundamentals

#### Abstract Classes Compared to Interfaces
Abstract classes are similar to interfaces. You cannot instantiate them, and they may contain a mix of methods declared with or without an implementation. However, with abstract classes, you can declare fields that are not static and final, and define public, protected, and private concrete methods. With interfaces, all fields are automatically public, static, and final, and all methods that you declare or define (as default methods) are public. In addition, you can extend only one class, whether or not it is abstract, whereas you can implement any number of interfaces.
* Which should you use, abstract classes or interfaces?
  * Consider using abstract classes if any of these statements apply to your situation:
    * You want to share code among several closely related classes.
    * You expect that classes that extend your abstract class have many common methods or fields, or require access modifiers other than public (such as protected and private).
    * You want to declare non-static or non-final fields. This enables you to define methods that can access and modify the state of the object to which they belong.
  * Consider using interfaces if any of these statements apply to your situation:
    * You expect that unrelated classes would implement your interface. For example, the interfaces Comparable and Cloneable are implemented by many unrelated classes.
    * You want to specify the behavior of a particular data type, but not concerned about who implements its behavior.
    * You want to take advantage of multiple inheritance of type.

### The Java Language

* Nester Types
  * Structure and scoping 
    * no relationship between instances of nested and enclosing types
      * static classes nested within classes
      * all classes nested within interfaces
      * all nested interfaces
  * Inner classes
    * each instances of nested class is associated with an instance of the enclosing class
    * anonymous classes are inner classes


### The Core Platform

* IO
  * InpustStream - binary
    - `int read(byte[] buf)`
  * OutputStream
  * Reader - text
    - `int read(char[] buf)`
  * Writer 


### HashMap

`Array` and `LinkedHashMap (with red-black tree)` are used to store nodes.

> This class makes no guarantees as to the order of the map; in particular, 
> it does not guarantee that the order will remain constant over time.

* If iteration performance is important:
  * do not set `capacity` to high (or `load factor` too low) in initialization

* default `load factor` is `.75`
  * `resizing threshold = load_factor * capacity`
* hash table is rehashed (rebuilt) when increasing, by twice size


> The **HashMap** class is roughly equivalent to **Hashtable**, except that :
> 1. it is unsynchronized 
> 2. and permits nulls, for both kye and value.  

```Java
// calculate the index of key
index = key.hash & (capicity - 1);
// which is equal to
index = key.hash % capicity;
```

* fail-fast

  If the `iterator` is invalid, it would throw `ConcurrentModificationException`

* Capacity of the internal table MUST be **power of 2**. Even input may be not, it would use:

```Java
// e.g. 13 which is 1101, whould output 1111 + 1 == 16
int tableSizeForCapicity(int cap) {
  int n = cap - 1;
  n |= n >>> 1;
  n |= n >>> 2;
  n |= n >>> 4;
  n |= n >>> 8;
  n |= n >>> 16;
  return (n < 0) ? 1 : n + 1; // simplized
}
```

Best choice for single thread.

### Hashtable

It is **synchronized**.

Any **non-null** object can be used as a key or value.

```Java
// implemented with array + linked list
/*
          TABLE 
    0 | node[n] | -> node[a] -> null;
    1 | node[x] | -> node[y] -> node[g] -> null;
    2 | node[l] | -> null
*/
Entry <>[] table;

class Entry {
  Entry next;
  // ...
}
```

### ConcurrentHashMap

It is thread-safe, fully interoperable with **Hastable**.

#### diff vs Hashtable
* Hashtable 
  * uses **single lock** to entire table
* ConcurrentHashMap 
  * uses **multiple lock** on **Segment** level (16 by default).
  * only lock for updates, not `get()`
  * it is more efficient

### LinkedHashMap/Set

Entries stored in **HashMap**, with *double-linked list* running through all its entries. In insertion-order or access-order.

* insertion-order is not affected when re-insert an existing entry
* Well-suited to building **LRU caches**

```JAVA
    final int MAX_CAPACITY = 5;
    LinkedHashMap<String, String> m = new LinkedHashMap<String, String>(MAX_CAPACITY + 1, .75F, true) {
      protected boolean removeEldestEntry(Map.Entry<String,String> eldest)  {
        return size() > MAX_CAPACITY; // return turn to remove eldest entry automatically
      }
    };
```

```Java
// how to get the "first" entry of a map?
Object key = linkedHashMap.keySet().iterator().next();
```































