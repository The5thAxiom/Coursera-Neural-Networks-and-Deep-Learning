# Higher Order Functions

## Higher Order Functions
- Functional languages treat functions like any other value and thus they can be returned as values and be passed as parameters
- A function that takes as parameters or returns, a function is called a higher-order function
- Eg 1: a function for sum of a function on all values between two numbers
    ```scala
    def id(x: Int): Int = x
    def cube(x: Int): Int = x * x * x
    def factorial(x: Int): Int = if x == 0 then 1 else x * factorial(x)

    def sum(f: Int => Int, a: Int, b: Int): Int =
        if a > b then 0
        else f(a) + sum(f, a + 1, b)
    ```
- Function Types
    - The type `A => B` is the type of a function which takes an argument of type A and returns a result of type B
- Anonymous Functions
    - Functinos without a name (no `def` used)
    - Function literals (like string or Int literals)
    - Eg 1: `(x: Int) => x * x * x`
    - The type can be omitted if the compiler can infer it by context
    - Anonymous functions are _syntactic sugar_, non-essential but nice to have (i disagree with the term, sugar is absolutely essential though) as they can be replaced by using a def
    - Eg 2: (using the `sum` function defined above)
        ```scala
        def sumInts(a: Int, b: Int) = sum(x => x, a, b)
        def sumCubes(a: Int, b: Int) = sum(x => x * x * x, a, b)
        ```

## Currying
- Why: to reduce repetition of parameters accross many functions
- Eg: rewriting the sum function
    ```scala
    def sum(f: Int => Int): (Int, Int) => Int =
        def sumF(a: Int, b: Int): Int =
            if a > b then 0
            else f(a) + sumF(a + 1, b)
        sumF
    ```
    - Here, sum returns a function and we can rewrite them as:
        ```scala
        def sumInts = sum(x => x)
        def sumCubes = sum(x => x * x * x)
        def sumFactorials = sum(factorial)

        // they can be used 
        sumCubes(1, 10) // normally
        ``` 
    - Now, (this is the fun part!), we can write them more simply:
        ```scala
        sum (cube) (1, 10)
        /*
        sum(cube) returns a function which takes 2 arguments, which are passed to it as (1, 10)
        */
        // if we use an anonymous function:
        sum(x => x * x * x)(1, 10)
        ```
- Multiple parameter lists
    - Currying, as done for the above example can get kinda clunky, so there is a special syntax in Scala for such functions:
        ```scala
        def sum(f: Int => Int)(a: Int, b: Int): Int =
            if a > b then 0 else f(a) + sum(f)(a + 1, b)
        ```
    - `def f(ps1)(ps2)...(psn) = E` is equivalent to `def f =>(ps1 => (ps2 => ...(psn => E)))`
    - In fact, we basically never need any parameters for a function, any function can just be a sequence of anonymous functions(each with a single parameter). This style of definition is called currying (named after ___Haskell Curry___ (the guy after which the language _Haskell_(the _daddy_(not exactly) of functional languages) was named!))
- More function types:
    - the sum function above (the curried one) has the type:
    `(Int => Int) => ((Int, Int) => Int)` or simply `(Int => Int) => (Int, Int) => Int` (a function which takes _a function which takes an Int and returns an Int_, and returns a function which takes two Ints and returns an Int)
    - `Int => (Int => Int)` is equivalent to `Int => Int => Int`

## Finding Fixed Points
- An example for trying out higher order functions
- A fixed point for a function $f$ is a point such that $f(x) = x$.
- How to find: 
- For some functions: applying f repetitively, (x, f(x), f(f(x))...) until the value doesn't change that much
    ```scala
    val tolerance = 0.0001

    def isCloseEnough(x: Double, y: Double) =
        abs((x - y)/x) < tolerance

    def fixedPoint(f: Double => Double)(firstGuess: Double): Double =
        def iterate(guess: Double): Double =
            val next = f(guess)
            if isCloseEnough(guess, next) then next
            else iterate(next)
        iterate(firstGuess)
    ```
    - This method doesn't work for every function:
        - for $f(x) = \sqrt{x}$, the square root of, say, $y$ is the fixed point of $f(y) = \frac{x}{y}$
        ```scala
        def sqrt(x: Double) =
            fixedPoint(y => x / y)(1.0)
        ```
        - this function, does not converge and will give an infinite loop (it oscillates between 1 and 2)
    - We can prevent such oscillations by averaging the values of the original sequence
        ```scala
        def sqrt(x: Double) =
            fixedPoint(y => (y + x/y)/2)(1.0)
        ```
        - This function works well
- Iterating and converging towards a fixed point by stabilizing the average is a general concept, and can be made its own function: 
    ```scala
    def averageDamp(f: Double => Double)(x: Double): Double =
        (x + f(x))/2
    ```

## Scala Syntax Summary
- This gave me flashbacks (bad ones) to my _Theory of Computation_ course XD

## Functions and Data
- Creating data structures using functions
- Designing a package for doing rational arithmetic
- Classes:
    ```scala
    class Rational(x: Int, y: Int):
        def num = x;
        def den = y;
    ```
    - This creates a new type and constructor.
    - Objects can be created : `val x = RationalNumber(1, 2)`
    - Their members can be selected by using the `.` operator: `x.num` and `x.den`
- Functions for adding (and multiplying) two rational numbers and for displaying a rational number
    ```scala
    def addRationalNumber(x: RationalNumber, y: RationalNumber): RationalNumber =
        RationalNumber(x.num * y.den + y.num * x.den, x.den * y.den)

    def makeString(x: RationalNumber): String =
        s"${x.num}/${x.den}"
    ```
- The complete class I made: (see the RationalNumbers.scala file for the latest version)
```scala
class RatNum(x: Int, y: Int):
    def num = x
    def den = y
    def add(a: RatNum) =
        RatNum(num * a.den + a.num * den, den * a.den)
    def subtract(a: RatNum)=
        RatNum(num * a.den - a.num * den, den * a.den)
    def multiply(a: RatNum) =
        RatNum(num * a.num, den * a.den)
    def divide(a: RatNum) =
        RatNum(num * a.den, den * a.num)
    def +(a: RatNum) =
        add(a)
    def -(a: RatNum) =
        subtract(a)
    def *(a: RatNum) =
        multiply(a)
    def /(a: RatNum) =
        divide(a)
    def neg = RatNum(-num, den)
    def unary_- = neg
    override def toString = s"${num}/${den}"
    // toString needs to be overriden cause its a function defined for the 'Object' class (remember, this is fully compatible with Java)
```

## Data Abstraction
- In the `RatNum` class, the number isn't always in its smallest form
- The simplification could be in each operation, but a simpler way is to put it in the constructor:
    ```scala
    class RatNum(x: Int, y: Int):
        private def gcd(a: Int, b: Int): Int =
            if b == 0 then a else gcd(b, a % b)
        private val g = gcd(x, y)
        def num = x/g
        def den = y/g
        // the rest is the same
    ```
- In both the cases (with and without gcd (and in any implementation of gcd)), the usage is the same. This is called data abstraction, you can change the implementation without ever bugging the users of that class
- The `this` keyword is used to access the current instance of the class (just like in C++, Java, etc)
- Preconditions: we can require certain things from the user like:
    ```scala
    class Rational(x: Int, y: Int):
        require(y > 0, "The denominator must be positive")
    ```
    - The `require` function is predefined.
    - Failing the condition will throw an `IllegalArhumentException`
- Assertions:
    - they are certain conditions to check the functioning of the code (not the user, that is `require`'s job)
    - Eg: `assert(x >= 0)` could be used in a sqrt function like: `val x = sqrt(y)`
    - Failing an assertion results in an `AssertionError`
- Constructors:
    - Primary Constructor: the constructor defined by defining a class
    - Auxiliary Constructors:
        ```scala
        class Rational(x: Int, y: Int):
            def this(x: Int) = this(x, 1)
        ```
- End markers:
    - for classes
        ```scala
        class thing(???):
            // the body of the class
        end thing
        ```
    - for functions
        ```scala
        def func(???): Int =
            // the body of the function
        end thing
        ```
    - for conditionals
        ```scala
        if ??? then // something
        else if ??? then ???
        else ???
        end if
        ```

## Evaluation and Operators
- Substitution method but for objects
- A constructor is just a normal function that returns a value (of the type of the class)
- How to evaluate: `C(v1, v2, ... vm).f(w1, w2, ... wn)` for a class `C(x1, ... xm) {... def f(y1, ... yn) = b ... }`
    - Substitute the parameters of f with the arguments
    - Substitute the parameters of C with the arguments
    - Substitute `this` with `C(v1, ... vm)`
- Extension methods:
    - A class might have quite a large number of methods, which isn't very modular.
    - Some methods don't need access to the internals of a class
    - They can be defined as extension methods:
        ```scala
        extension (r: RatNum)
            def min(s: Ratnum): Boolean = if s.less(r) then s else r
            def abs: RatNum = RatNum(r.num.abs, r.den)
        ```
    - Extensions are visible if they are defined with the class or visible in the current scope.
    - Anyone can define extensions wherever they want
    - Extensions can only add new members and not override existing ones
    - Extensions cannot refer to other class members via `this`
- Operators:
    - Operators are also identifiers in scala and can be used as method names:
        ```scala
        extension (x: RatNum)
            def + (y: RatNum): RatNum = x.add(y)
            def * (y: RatNum): RatNum = x.multiply(y)
        ```
    - Now, we can use these operators like we do on operators: `val a = RatNum(1, 2); val b = RatNum(3, 2); val c = a * a - b`
    - `a.+(b)` is the same as `a + b`, as they are methods with a single parameter
    - Any method with a single parameter can be made infix like this: 
        ```scala
        extension(a: Int)
            infix def mod(b: Int): Int = a % b
        
        // usage:
        val x = 5 mod 3 // val x: Int = 5
        ```
- Precedence:
    - Determined by the first character
    ```
    <special characters>
    * / %
    + -
    :
    = !
    < >
    &
    ^
    |
    <all letters (including _)>
    ```
    - Question: parenthesize: `a + b ^? c ?^ d less a ==> b | c`
        - Answer: `(((a + b) ^? (c ?^ d)) less ((a ==> b) | c))`

##