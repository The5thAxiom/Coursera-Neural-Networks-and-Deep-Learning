# Data And Abstraction

## Class Heirarchies
- Abstract classes:
    - Classes which contain members without implementation (only the names and types, basically)
    - Abstract classes cannot be instantiated
    -   ```scala
        abstract class IntSet:
            def incl(x: Int): IntSet
            def contains(x: Int): Boolean
        ```
- Class extensions
    - Implementing sets as binary trees
    - Two types of trees are possible: a tree for the empty set and a tree consisting of an integer and two sub-trees
    -   ```scala
        class Empty() extends IntSet:
            def contains(x: Int): Boolean = false
            def incl(x: Int): IntSet = NonEmpty(x, Empty(), Empty())
        ```
    - The set $\{1, 2, 4, 5\}$ will have a binary search tree, this makes it easier to query
    -   ```scala
        class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet:
            def contains(x: Int): Boolean =
                if x < elem then left.contains(x)
                else if x > elem then right.contains(x)
                else true
            end contains
            
            def incl(x: Int): IntSet =
                if x < elem then NonEmpty(elem, left.incl(x), right)
                else if x > elem then NonEmpty(elem, left, right.incl(x))
                else this
            end incl
        end NonEmpty
        ```
    - On inserting a new element, a new tree will be created, leaving the old one behind, thus, it is a _persisten data structure_ (which are used a lot in functional programming)
- Base Classes and Subclasses
    - If a class `A` extends a class `B`, then `A` is a subclass of `B` and `B` is a superclass of `A`.
    - Any direct or indirect superclass of a class `C` are it's base classes.
    - In Scala, any user defined class always extends another class(if no superclass is given explicity, it means it is extending the `Object` class in the Java package `java.lang`)
- Implementation and Overriding
    - A subclass can _implement_ an abstract functions (defined in an abstract class).
    - An existing, non-abstract function can be redifined by _overriding_ it.
    - Eg 1:
        ```scala
        abstract class Base:
            def foo = 1
            def bar: Int
        end Base

        class Sub extends Base:
            override def foo = 2 // overriding
            def bar = 3 // implementing
        end Sub
        ```
- Object definitions
    - In the `IntSet` example, there is no need for separate `Empty` objects (as its kind-of a null value marker), so we can define an object (instead of a whole class, kinda like javascript):
        ```scala
        object Empty extends IntSet:
            def contains(x: Int): Boolean = false
            def incl(x: Int): IntSet = NonEmtpy(x, Empty, Empty)
        ```
    - This is called a singleton object (a value)
- Companions:
    - Objects(values) and classes(types) have separate namespaces (the term namespace and the type namespace) and thus, an object can have the same name as a class
    - If a class and object are given the same name in the same sourcefile, they are called _companions_(aww)
    - Eg:
        ```scala
        class Thing ...
        object Thing ...
        ```
    - Calling a method from the object is kindof like calling a method from a static class(or a static method) in Java (Scala doesn't have static classes/methods/members ('coz Scala is a purer OOP language than Java (OMG its THE BEST)))
- Programs
    - Scala files can be compiled into standalone applications
    - Eg:
        ```scala
            object Hello:
                def main(args: Array[String]): Unit =
                    println("Hello, World!")
                end main // optional
            end Hello // optional
        ```
    - An easier way of writing the main method:
        ```scala
        @main
        def birthday(name: String, age: Int) = // 2 command line arguments
            println(s"Happy birthday, ${name}!, ${age} ke ho gaye ho")
        ```
- Dynamic Binding
    - OOP languages need to implement _dynamic method dispatching_.
    - What that means is that the method calls depend on the runtime type of the object that contains the method
    - This infact, happens automatically by the substitution method of evaluation expressions
    - Dynamic dispatch of method is analogous to calls to higher-order functions (infact, we can implement them in terms of each other!)

## Organising Classes
- Packages:
    - Classes and objects are organised in packages.
    - We can create a package like this:
        ```scala
        package progfun.examples // the top of the source file
        
        object Hello
            ...
        ```
    - The _fully qualified_ name of the object `Hello` is now: `frogfun.examples.Hello`
    - Normally, the directory structure of the files would follow the package naming, so the above file would be: `progfun/examples/Hello.scala` (this isn't cumpulsory)
- Importing:
    - Using a package name can get quite cumbersome: `val r = OMNIS.Scala.BinaryTree(1)`
    - We can use imports to simplify them:
        ```scala
        import OMNIS.Scala.BinaryTree

        val r = BinaryTree(1)
        ```
    - Kinds of imports:
        - `import OMNIS.Scala.BinaryTree` only BinaryTree (named import)
        - `import OMNIS.Scala.{BinaryTree, LinkedList}` both BinaryTree and LinkedList (named import)
        - `import OMNIS.Scala._` everything inside OMNIS.Scala (wildcard import)
    - packages are also kinda objects (even objects can be imported)
    - Automatic Imports:
        - all members of : `scala`, `java.lang` (ofc) and all singleton objects of `scala.Predef`
        - Fully qualified names of some types:
            - `Int` is `scala.Int`
            - `Boolean` is `scala.Boolean`
            - `Object` is `java.lang.Object`
            - `require` is `scala.Predef.require`
            - `assert` is `scala.Predef.assert`
- Traits
    - In Scala (and Java), a class can only have one superclass (caues the diamond problem)
    - For multiple inheritence, we use traits
    - Eg:
        ```scala
        train Planar:
            def height: Int
            def width: Int
            def surface = height * width
        ```
    - Classes, objects and traits can inherit only one superclass but any number of traits.
    - Traits are kinda like interfaces in Java but are more powerful (as they can have parameters and other stuff)
- Class Heirarchy of Scala
    - Top types:
        - `Any`:
            - The base type of all types
            - Methods: `==`, `!=`, `equals`, `hashCode`, `toString`
        - `AnyRef`:
            - The base type of all reference types (classes i THINK)
            - An alias of `java.lang.object`
        - `AnyVal`: The base type of all primitive types (`Byte`, `Short`, `Int`, `Long`, `Float`, `Double`, `Char` and `Boolean`)
    - `Nothing`:
        - At the bottom of the class heirarchy
        - A subtype of every other type
        - Has no value
        - Userful to signal abnormal termination and as an element type of empty collections
- Exceptions:
    - Similar to Java
    - Eg: `throw Exc`

## Polymorphism
- Cons-Lists:
    - Immutable linked list
    - Constructed from: Nil and Cons
    - `List(1, 2, 3)` has 3 `Cons` cells (a binary tree with Cons.left as the value and Cons.right as the next Cons) and a Nil at the end (to the right of the final Cons)
    - Lists can contain other lists (even lists of different types)
    - We will assume just Ints for now,
    ```scala
    trait IntList ...
    class Cons(val head: Int, val tail: IntList) extends IntList // see the value parameters section below
    class Nil() extends IntList
    ```
    - A list is either: 
        - an empty list `Nil()`
        - a list `Cons(x, xs)` where x is the head and xs is the tail
- Value parameters:
    - defining the parameters and fields of the class at the same time
    - `class Cons(val head: Int, val tail: IntList) extends IntList` is equivalent to:
        ```scala
        class Cons(_head: Int, _tail: IntList) extends IntList:
            val head = _head
            val tail = _tail
            // _head and _tail aren't being used anywhere else
        ```
- Type parameters:
    - OMG OMG YESS
    - Generalizing by giving type parameters:
        ```scala
        trait IntList[T] ...
        class Cons[T](val head: T, val tail: IntList[T]) extends IntList[T]
        class Nil[T]() extends IntList[T]
        ```
        - Do not affect the substitution
- Defining a list:
    ```scala
    trait List[T]:
        def isEmpty: Boolean
        def head: T
        def tail: List[T]
    end List

    class Cons[T](val head: T, val tail: List[T]) extends List[T]:
        def isEmpty = false
    end Cons

    class Nil[T] extends List[Int]:
        def isEmpty = true
        def head = throw new NoSuchElementException("Nil.head")
        def tail = throw new NoSuchElementException("Nil.tail")
    ```
- Generic Functions:
    - `def singleton[T](elem: T) = Cons[T](elem, Nil[T])` can be used like: `singleton[Int](1)` or `singleton[Boolean](true)`
    - The compiler can usually guess the type paramters from the values `singleton(1)` also works
- Type erasure:
    - All type parameters and type arguments get removed before evaluation
    - Other languages which do this: Java, Haskell, ML, OCaml
    - Languages which don't: C++, C#, F#
- Polymorphism:
    - A function can have arguments/instances of many different types
    - 2 kinds:
        - subtyping: instances of a subclass passed to a base class
        - generics: functions or classes created by type parametrization
