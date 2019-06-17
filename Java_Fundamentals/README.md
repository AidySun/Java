
# Java Fundamentals

<!-- MarkdownTOC autolink="true" levels="1,2,3,4" -->

- [The Java Language](#the-java-language)
  - [The Core Platform](#the-core-platform)
  - [Abstract Classes Compared to Interfaces](#abstract-classes-compared-to-interfaces)
  - [HashMap](#hashmap)
  - [Hashtable](#hashtable)
  - [ConcurrentHashMap](#concurrenthashmap)
  - [diff vs Hashtable](#diff-vs-hashtable)
  - [LinkedHashMap/Set](#linkedhashmapset)
- [Tech](#tech)
  - [JSP](#jsp)
  - [Servlet](#servlet)
  - [Session](#session)
  - [RMI](#rmi)
  - [Dynamic Proxy](#dynamic-proxy)
  - [AJAX](#ajax)
- [Dev Tools](#dev-tools)
  - [`jconsole`](#jconsole)
  - [`jps`](#jps)
  - [`jstat`](#jstat)
  - [`jstatd`](#jstatd)

<!-- /MarkdownTOC -->

## The Java Language

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

### Abstract Classes Compared to Interfaces
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

### diff vs Hashtable
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


## Tech

### JSP
Java Serve page

### Servlet
*Small* Java program

### Session
Stateful connection. 

* server end
  * not friendly to cluster
    * store sessions on shared storage 
    * force peering between client to server
* client end
  * cookie
  * security issue


### RMI
*reference from Data-Structure-and-Algorithm*

```Java
// 1. server service object
java.rmi.remote
java.rmi.server.UnicastRemoteObject
// 2. generate proxy objects stub/skel
rmic MyServiceImpl
// 3. registry
Naming.rebind()
// 4. client
Naming.lookup(rmi://127.0.0.1/MyService)
// client can get stub at compile time, or using dynamic class downloading at runtime
```
* `transient` - not to serialize the property

### Dynamic Proxy
* Dynamic - means the `Proxy` **class** is created dymanically at runtime.
  * it doesn't mean to map the hander and real object at runtime.
  * there is no specific proxy class was decleared when starting

```Java
class PersonBean { ... } // real object

class OwnerInvocationHandler implements InvocationHandler {
  PersonBean person;
  public OwnerInvocationHandler(Person p) {
    person = p;
  }

  public Object invoke(Object proxy, Method m, Object[] args) {
    try {
      if (m.getName().equals("setSalary")) {
        throw new IllegalAccessException(); // self cannot set salary
      } else {
        return m.invoke(person, args);
      }
    } catch (InvocateTargetException e) {
      e.printStackTrace();
    }
    return null;
  }
}

// create proxy class
PersonBean getOwnerProxy(PersonBean p) {
  return (PersonBean) Proxy.newProxyInstance(
    p.getClass().getClassLoader(),
    p.getClass().getInterfaces(),
    new OwnerInvocationHandler(p));
}

// usage
PersionBean p = new PersonBean();
PersonBean ownerProxy = getOwnerProxy(p);
ownerProxy.setSalary(1000000);  // ohh, exception
```

### AJAX
Asyncronous Javascript And XML (JSON in nowadays)

## Dev Tools

### `jconsole`
* It a graphic user interface and a monitoring tool compiles to Java Management Extension (**JMX**).
* Location `JDK_HOME/bin`
* usage
  ```shell
  $ jconsole
  $ jconsole processID
  $ jconsole hostName:port  # remote
  ```

### `jps`

* List JVM on target system

### `jstat`

* Monitor JVM statistics

### `jstatd`

* monitor the creation and termination of Java HotSpot VMs.


























