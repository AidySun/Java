# Notes of [Design Patterns in Java](https://app.pluralsight.com/paths/skills/design-patterns-in-java)

## TOC
1. [Creational](#creational)
2. [Structural](#structural)
3. [Behavioral](#behavioral)

----

# CREATIONAL

### Singleton
  1. Lazily loaded
  2. Only one instance created
  3. Should be thread safe
      * `volatile` key to private instance property
      * ensure no reflection is used 
  ```Java
  private static volatile MySingleton instance = null;

  private MySingleton() {
    // the constructor should be called by getInstance() only, and only once
    if (instance != null) {
        throw new RuntimeException("Use getInstance() to create");
    }
  }

  // `getInstance()` should NOT have parameters, if it had, that violates singleton principles, 
  // then it should be a factory pattern.
  public MySingleton getInstance() {
    if (instance == null) {
        synchronized(MySingleton.class) {
            if (instance == null) {
                instance = new MySingleton();
            }
        }
        return instance;
    }
  }
  ```
  * if there is another static property, its get method should NOT be static, because it should be called via singleton instance object.

### Builder
For objects have multiple steps or parameters to create, using Builder to avoid long parameters or many setters.

  * Object's class seperates with its creating process
  * ? Objects created are immutable (no setter methods)
  * Handles complex constructors
    * large num of parameters (telescoping) 
    * lots of setters
  * Examples: `StringBuilder, Locale.Builder`

  ```Java
  // Builder general should be an inner static class
  class Lunch {
    final private String meat;

    public static class Builder {
        private String meat;
        private String drink;

        public Builder meat(String meatname) {
            this.meat = meatname;
            return this;
        }

        public Builder drink(String drink) {
            this.drink = drink;
            return this;
        }

        public Builder build() {
            return new Lunch(this); // similar to StringBuilder.toString()
        }
    }

    public Lunch(Builder b) {
        meat = b.meat;
    }
    public getMeat() { return meat; }

    // NO setter is needed 
  }

  // Usage
  Lunch.Builder lb = new Lunch.Builder();
  lb.meat("beef").bread("xxx").drink("yyyy");
  Lunch lunch = lb.build();
  ```
  * Builder contrast with Prototype
    * Builder handles complex constructors, while Prototype avoids calling complex constructors.
    * Builder has no interfaces.
    * Objects created by builder is immutable.

### Prototype
Pre-create some instances, clone based on them when needed rather than new. Similiar with cache.
  * Avoids costly/weight creation (e.g. lots of condition/calculation during creation, or complex permission checking)
    * Register can load/create costly objects as *templates/prototypes*, return the clone of object itself to create a new object instance.
    * Lighter weight construction
      * using copy constructor or clone
  * Avoids subclassing
  * Typically doesn't use "new" directly
  * Often utilizes an Interface
  * Usually implemented with a Registry/Factory and clone of itself
  * opposite of Builder - prefer clone rather than new
  * Shallow v.s. Deep Copy
    * class (not interface or abstract class) implements `Cloneable` is shallow copy for objects
  ```Java
  abstract class Item implements Cloneable {
    @Override
    protected Object clone() {
        return super.clone();
    }
  }

  class SubItem extends Item {
  }

  class Registry { // also can be Factory/Cache/Store
    private Map<String, Item> items = new HashMap<>();

    public Registry() {
        loadDefaultItems();
    }
    public Item createItem(String name) {
        return (Item)(items.get(name)).clone();
    }
  }
  // Usage
  Registry registry = new Registry();
  SubItem subItem = (SubItem) registry.createItem("SubItem");
  Item anotherDifferentSubItem = registry.createItem("SubItem");
  ```
  * Pros and Cons
    * Pros
      * clone objects without coupling to their concrete classes
      * produce complex objects more conveniently
      * an alternative to inheritance, e.g. one class with different configurations
    * Cons
      * cloning complex objects that have **circular references** might be very tricky

### Factory
  * Parameter driven - parameterized create method
  * Works together with interface/abstract classes and subclasses
  * Real creation occurs in concrete/sub classes
  * `Calendar, NumberFormat`
  ```Java
  class abstract Website {
    private List<Page> pages;
    public Website() {
        create();
    }
    public abstract void create();
  }

  class BlogWebsite extends Website {
    @Override
    public void create() {
        pages.add(new AboutPage());
        pages.add(new ArticlePage());
    }

    class WebsiteFactory {
        public static Website get(String type) {
            if (type == "blog") {
                return new BlogWebsite();
            } // else ...
        }
    }
  }
  ```

### AbstractFactory
Abstract factory is two (or more) layer of factory, super factory gets input parameter to decide which sub-factor is returned.
Sub-factory uses parameter input to decide with sub-class of object is created.
  * Factory of Factories
    * Group of similar factories
  * Heavy abstraction
  * Defer to subclasses
  * Parameterized create method
  * `DocumentBuilderFactory`
  ```Java
  public abstract class CreditCardFactory {
    public static CreditCardFactory getFactory(int score) {
        if (score > 100) return new VisaFactory();
        // else ...
    }
    public abstract CreditCart getCart(String cardType);
    public abstract CardValidator getCardValidator(String cardType);
  }

  public class VisaFactory extends CreditCardFactory {
    @Override
    public CreditCart getCart(String cardType) {
        // return VisaGoldCreditCard or VisaSilverCreditCard
    }
  }

  public abstract class CreditCart { }
  public  class VisaGoldCreditCart extends CreditCardFactory { }
  // same for CardValidator
  ```
  * Pitfalls
    * Complexity
    * Runtime switch
    * Pattern within a pattern
    * Problem specific
    * Starts as a Factory


# STRUCTURAL

### Adaptor
It's a kind of wrapper or convertor. Generally work with legeacy code.
  ```Java
  public interface Employee {
    public String getName();
    public int getID();
    // ...
  }

  public class EmployeeAdapterOldData implements Employee {
    private OldData instance;

    @Override String getName() {
      instance.getFirstName();
    }

  }
  ```

An interface of a class like `Employee`, the methods generally are getters & setters.

### Bridge
  * When there are (more than) two different dimensions that could change seperately. (e.g. Shape and Color, platform and type)
  * Decouple abstract class and implementation class. 
  * Avoid subclass-expanding
  * Kind of composition is better than inheritance.
  * Runtime composition but not compile-time composition
  * Abstract class and Implementation class are all interfaces (abstract visual classes), they have their own sub-classes
  ```Java
  // Java
  interface Shape {
    private Color color;
  }
  
  class Square implements Shape {
  }
  
  interface Color {
  }
  
  class Red implements Color {
  }
  ```
  
  ```CPP
  // C++
  class Messager {
  private:
    MessagerImpl *impl;
  public:
    void func0101() {
      impl->funcbbbbb();
    }
    virtual void funca() = 0;
  }
  
  class MessageImpl {
  public:
    virtual int funcbbbbb() = 0;
  }
  ```
  * Cons of Inheritence
    * Force injection - subclass MUST inherit everything of super class
    * Any change to super class would affect all its children











# Behavioral



















https://app.pluralsight.com/player?course=design-patterns-java-creational