
# Java Fundamentals

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