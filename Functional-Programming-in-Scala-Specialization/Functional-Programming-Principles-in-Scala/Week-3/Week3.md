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

## Objects Everywhere
- Scala is a _pure_ OO language (every value is an object).
- Even functions and primitive types are objects.
- For efficiency, the Scala compiler uses 32-bit integers for `Int`s and Java's Boolean for `Boolean`, but conceptually, they are like any other class defined in the language (no special treatment)
- Booleans:
    - Booleans map to the JVM's primitive boolean type
    - But could be defined like any other class
- Any primitive type could be implemented that way
- However, defining them recursively (eg: natural numbers as Peano numbers) is very inefficient

## Funtions as Objects
- Functions, like any other type can be classes
- Function types:
    - `A => B` is an abbreviation of `scala.Function[A, B]`
    -   ```scala
        package scala
        trait Function[A, B]:
            def apply(x: A): B
        ```
- Function values:
    - `(x: Int) => x * x` will be expanded as:
        - `new Function[Int, Int]: def apply(x: Int) = x * x`
        - An anonymous function can be thought of as a block:
            ```scala
            {
                class $anonfun() extends Function1[Int, Int]:
                    def apply(x: Int) = x * x
                $anonfun()
            }
            ```
- Function calls
    - Expansion: `f(a, b)` becomes `f.(a, b)`
    - Eg:
        -   ```scala
            val f = (x: Int) => x * x
            f(7)
            ```
        - becomes:
        -   ```
            val f = new Function[Int, Int]:
                def apply(x: Int) = x * x
            f.apply(7)
            ```






# backup code
```scala
//------------------------------------------------

package objsets

import TweetReader.*

/**
 * A class to represent tweets.
 */
class Tweet(val user: String, val text: String, val retweets: Int):
  override def toString: String =
    "User: " + user + "\n" +
    "Text: " + text + " [" + retweets + "]"

/**
 * This represents a set of objects of type `Tweet` in the form of a binary search
 * tree. Every branch in the tree has two children (two `TweetSet`s). There is an
 * invariant which always holds: for every branch `b`, all elements in the left
 * subtree are smaller than the tweet at `b`. The elements in the right subtree are
 * larger.
 *
 * Note that the above structure requires us to be able to compare two tweets (we
 * need to be able to say which of two tweets is larger, or if they are equal). In
 * this implementation, the equality / order of tweets is based on the tweet's text
 * (see `def incl`). Hence, a `TweetSet` could not contain two tweets with the same
 * text from different users.
 *
 *
 * The advantage of representing sets as binary search trees is that the elements
 * of the set can be found quickly. If you want to learn more you can take a look
 * at the Wikipedia page [1], but this is not necessary in order to solve this
 * assignment.
 *
 * [1] http://en.wikipedia.org/wiki/Binary_search_tree
 */
abstract class TweetSet extends TweetSetInterface:

  /**
   * This method takes a predicate and returns a subset of all the elements
   * in the original set for which the predicate is true.
   *
   * Question: Can we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def filter(p: Tweet => Boolean): TweetSet =
    this.filterAcc(p, Empty())

  /**
   * This is a helper method for `filter` that propagates the accumulated tweets.
   */
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet

  /**
   * Returns a new `TweetSet` that is the union of `TweetSet`s `this` and `that`.
   *
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def union(that: TweetSet): TweetSet

  /**
   * Returns the tweet from this set which has the greatest retweet count.
   *
   * Calling `mostRetweeted` on an empty set should throw an exception of
   * type `java.util.NoSuchElementException`.
   *
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def mostRetweeted: Tweet = this.rtAcc(Tweet("x", "x", 0))
  def rtAcc(max: Tweet): Tweet

  /**
   * Returns a list containing all tweets of this set, sorted by retweet count
   * in descending order. In other words, the head of the resulting list should
   * have the highest retweet count.
   *
   * Hint: the method `remove` on TweetSet will be very useful.
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def descendingByRetweet: TweetList = this.dIter(Nil)

  def dIter(acc: TweetList): TweetList

  /**
   * The following methods are already implemented
   */

  /**
   * Returns a new `TweetSet` which contains all elements of this set, and the
   * the new element `tweet` in case it does not already exist in this set.
   *
   * If `this.contains(tweet)`, the current set is returned.
   */
  def incl(tweet: Tweet): TweetSet

  /**
   * Returns a new `TweetSet` which excludes `tweet`.
   */
  def remove(tweet: Tweet): TweetSet

  /**
   * Tests if `tweet` exists in this `TweetSet`.
   */
  def contains(tweet: Tweet): Boolean

  /**
   * This method takes a function and applies it to every element in the set.
   */
  def foreach(f: Tweet => Unit): Unit

class Empty extends TweetSet:

  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = acc

  def union(other: TweetSet): TweetSet = other

  override def mostRetweeted: Tweet = throw java.util.NoSuchElementException()

  def rtAcc(max: Tweet): Tweet = max

  override def descendingByRetweet: TweetList = Nil

  def dIter(acc: TweetList): TweetList = acc

  /**
   * The following methods are already implemented
   */

  def contains(tweet: Tweet): Boolean = false

  def incl(tweet: Tweet): TweetSet = NonEmpty(tweet, Empty(), Empty())

  def remove(tweet: Tweet): TweetSet = this

  def foreach(f: Tweet => Unit): Unit = ()
end Empty

class NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet) extends TweetSet:

  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = 
    def allElseFiltered = acc.union(this.left.filterAcc(p, Empty())).union(this.right.filterAcc(p, Empty()))
    if p(this.elem) then allElseFiltered.incl(this.elem)
    else allElseFiltered
  end filterAcc
  
  def union(other: TweetSet): TweetSet =
    this.left.union(this.right).union(other).incl(this.elem)

  def rtAcc(max: Tweet): Tweet =
    def newMax: Tweet =
        if this.elem.retweets > max.retweets then this.elem
        else max
    def leftMax = this.left.rtAcc(newMax)
    def rightMax = this.right.rtAcc(newMax)
    if leftMax.retweets > rightMax.retweets then leftMax
    else rightMax

  def dIter(acc: TweetList): TweetList = 
    def max = this.mostRetweeted
    Cons(max, this.remove(max).dIter(Nil))
  /**
   * The following methods are already implemented
   */

  def contains(x: Tweet): Boolean =
    if x.text < elem.text then
      left.contains(x)
    else if elem.text < x.text then
      right.contains(x)
    else true

  def incl(x: Tweet): TweetSet =
    if x.text < elem.text then
      NonEmpty(elem, left.incl(x), right)
    else if elem.text < x.text then
      NonEmpty(elem, left, right.incl(x))
    else
      this

  def remove(tw: Tweet): TweetSet =
    if tw.text < elem.text then
      NonEmpty(elem, left.remove(tw), right)
    else if elem.text < tw.text then
      NonEmpty(elem, left, right.remove(tw))
    else
      left.union(right)

  def foreach(f: Tweet => Unit): Unit =
    f(elem)
    left.foreach(f)
    right.foreach(f)
end NonEmpty

trait TweetList:
  def head: Tweet
  def tail: TweetList
  def isEmpty: Boolean
  def foreach(f: Tweet => Unit): Unit =
    if !isEmpty then
      f(head)
      tail.foreach(f)

object Nil extends TweetList:
  def head = throw java.util.NoSuchElementException("head of EmptyList")
  def tail = throw java.util.NoSuchElementException("tail of EmptyList")
  def isEmpty = true

class Cons(val head: Tweet, val tail: TweetList) extends TweetList:
  def isEmpty = false


object GoogleVsApple:
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
  val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad")

  def iter(list: List[String], x: Tweet, g: TweetSet): Unit =
    if list.isEmpty then return
    else if x.text.contains(list.head) then iter(list.tail, x, g.incl(x))
    else (iter(list.tail, x, g))

  lazy val googleTweets: TweetSet = 
    println("doing google")
    def all: TweetSet = TweetReader.allTweets
    def g: TweetSet = Empty()
    all.foreach((x: Tweet) => iter(google, x, g))
    g

  lazy val appleTweets: TweetSet = 
    println("doing apple")
    def all: TweetSet = TweetReader.allTweets
    def g: TweetSet = Empty()
    all.foreach((x: Tweet) => iter(apple, x, g))
    g

  /**
   * A list of all tweets mentioning a keyword from either apple or google,
   * sorted by the number of retweets.
   */
  lazy val trending: TweetList = googleTweets.union(appleTweets).descendingByRetweet

object Main extends App:
  // Print the trending tweets
  GoogleVsApple.trending foreach println


//------------------------------------------------
```