
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