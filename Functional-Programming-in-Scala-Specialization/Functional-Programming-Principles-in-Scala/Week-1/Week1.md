# Getting Started
## Coures Introduction
- Scala is very popular in the functional programming world.
- Works well with OOP stuff (as it comiples to JVM iirc)

## Working on Assignments
- Always take the graded assignments (which don't say 'practice' or 'audit version')

## Tools Setup
- jvm and sbt (Scala build tool)
- i have the jdk
- and reinstalled sbt
- this is the worst part of starting any new programming language, the complete hell of setting it up
- use powershell to use sbt (i think git bash is causing some issues)

# Functions & Evaluation
## Programming Paradigms
- Scala is a functional programming language
- Forget what you know and jump into functional programming head-on
## Elements of Programming
- Scala REPL (read, eval, print loop) is started by typing `sbt console` (`Scala` works if that is installed but i only have sbt rn)
- Types:
    - Scala types are the same as java but start with caps:
        - Byte
        - Short
        - Int
        - Long
        - Float
        - Double
        - Char
        - Boolean
    - Types given after the name, eg: `val x: Int = 5`
- Substitution model: 
    - A way of evaluating expressions by substituting values.
    - Can be applied to any pure function(no side effects)
    - Formalised in $\lambda$-calculus
    - Every expression can't be reduced to a value, eg: `def loop: Int = loop`
    - Two ways:
        - Call by value: evaluate function arguments first
        - Call by name(cue song): function evaluated first
    - Both ways reduce to the same value iff:
        - both expressions consit of pure functions
        - both evaluations terminate

## Evaluation Strategies and Termination
- If the CBV evaluation of an expression terminates, then the CBN evaluation will also terminate (not true for the opposite direction).
- Eg: 
    ```Scala
    def first(x: Int, y: Int) = x
    def loop: Int = loop
    def frist(1, loop) //CBN terminates, CBV does not
    ```
- Scala normally uses CBV because:
    - Saves time (sometimes reduces exponentially many evaluations into 1)
    - If a function has side-effects, CBV is more predictable
- Using CBN in Scala: `def constOne(x: Int, y: => Int) = x`
- Evaluation of above eg:
    - `constOne(1 + 2, loop)`
        - constOne(1 + 2, loop)
        - constOne(3, loop)
        - 3 (how does this terminate!?!?)
    - `constOne(loop, 1 + 2)`
        - constOne(loop, 1 + 2)
        - constOne(loop, 1 + 2)
        - this goes on forever

## Conditionals and Value Definitions
- Conditionals are basically ternaries
    - eg: 
        ```Scala
        deg abs(x: Int) = if x >= 0 then x else -x
        ```
    - `x >= 0` is a _predicate_, a kind-of boolean?
- Booleans:
    - `true` or `false`
    - not `!b`, and `a && b`, or `a || b`
    - usual operators
    - `&&` and `||` are _short-circuited_ as in Java
- Value Definitions
    - The `def` keyword is CBN (evaluated at each use) and `val` is CBV.
    - If `def loop: Boolean = loop` is a function, then:
        - `def x = loop` works as _def_ is CBN
        - `val x = loop` will cause an infinite loop as it is evaluated then and there and never terminates
    - `and` and `or` functions: (my implementation)
        ```scala
        def and(x: Boolean, y: Boolean): Boolean = if x == false then false else y
        def or(x: Boolean, y: Boolean): Boolean = if x == true then true else y
        ```
    - Instructor's implementation:
        ```scala
        def and(x: Boolean, y: => Boolean): Boolean = if x then y else false // should be call by name (for proper short-circuiting)
        ```

## Square Roots with Newton's Method
- Algorithm: 
    - Take an initial estimate `y`
    - Improve the estimate by taking the mean of y and `x`/`y`
- 1st: a function which computes a single step
    ```scala
    def sqrtIter(guess: Double, x: Double): Double = 
        if isGoodEnough(guess, x) then guess
        else sqrtIter(improve(guess, x), x)
    ```
- 2nd: a function to improve the estimate
    ```scala
    def improve(guess: Double, x: Double) = (guess + x/guess) / 2
    def isGoodEnough(guess: Double, x:Double) = abs(guess * guess - x) < 0.001
    ```
- The `isGoodEnough` function is not that good for smaller numbers cause 
- Finally, the sqrt function:
    ```scala
    def sqrt(x: Double) = sqrtIter(1.0, x)
    ```

## Blocks and Lexical Scope
- In the previous example, only the `square` function is needed for usage, all others are implementation details (not meant to be accessed by the user), but all are in the global namespace.
- This has _polluted_ the global namespace with useless functions
- To remedy that, we can include the other functions inside `sqrt`
- Like this: 
    ```scala
    def abs(x: Double): Double = if x >= 0 then x else -x
    def square(x: Double) = x * x

    def sqrt(x: Double) = {
        def improve(guess: Double, x: Double) = (guess + x / guess) / 2
        def isGoodEnough(guess: Double, x:Double) = abs(guess * guess - x) < 0.001
        def sqrtIter(guess: Double, x: Double): Double = 
                if isGoodEnough(guess, x) then guess
                else sqrtIter(improve(guess, x), x)
        sqrtIter(1.0, x)
    }
    ```
- Blocks
    - Delimited by `{` and `}`
    - Contain a sequence of definitions or expressions
    - The last element is an expression that defines the block's value
    - In scala3, we can write it like:
        ```scala
        def sqrt(x: Double) = 
            def improve(guess: Double, x: Double) = (guess + x / guess) / 2
            def isGoodEnough(guess: Double, x:Double) = abs(guess * guess - x) < 0.001
            def sqrtIter(guess: Double, x: Double): Double = 
                    if isGoodEnough(guess, x) then guess
                    else sqrtIter(improve(guess, x), x)
            sqrtIter(1.0, x)
        ```
    - Any definitions inside the block are only visible inside that block
- Shadowing
    - The definitions inside a block _shadow_ the definitions outside (basically, if there is a value `x` inside a block, any futher call to `x` refers to the one inside the block, and any outside values called `x` are shadowed)
- Lexical scoping:
    - Definitions from outside the block are visible inside if not shadowed
    - Thus, we can further simplify `sqrt` like:
        ```scala
        def sqrt(x: Double) = 
            def improve(guess: Double) = (guess + x / guess) / 2
            def isGoodEnough(guess: Double) = abs(guess * guess - x) < 0.001
            def sqrtIter(guess: Double): Double = 
                    if isGoodEnough(guess) then guess
                    else sqrtIter(improve(guess))
            sqrtIter(1.0)
        ```
- Semicolons
    - If there are more than one statements on a single line, the can be separated by semicolons
    - We can even end lines with semicolons, but that isn't ___idomatic___
## Tail Recursion
- Rewriting using the subsitution principle
    - eg 1: `def gcd(a: Int, b: Int): Int = if b == 0 then a else gcd(b, a%b)`
        - `gcd(14, 21)`
        - `if 21 == 0 then 12 else gcd(21, 14 % 21)`
        - `if false then 12 else gcd(21, 14 % 21)`
        - `gcd(21, 14 % 21)`
        - `gcd(21, 14)`
        - `if 14 == 0 then 21 else gcd(14, 21 % 14)`
        - `if false then 21 else gcd(14, 21 % 14)`
        - `gcd(14, 21 % 14)`
        - `gcd(14, 7)`
        - `if 7 == 0 then 14 else gcd(7, 14 % 7)`
        - `if false then 14 else gcd(7, 14 % 7)`
        - `gcd(7, 14 % 7)`
        - `gcd(7, 0)`
        - `if 0 == 0 then 7 else gcd(0, 7 % 0)`
        - `if true then 7 else gcd(0, 7 % 0)`
        - `7`
    - eg 2: `def factorial(x: Int): Int = if x == 0 then 1 else x * factorial(x - 1)`
        - `factorial(4)`
        - `if 4 == 0 then 1 else 4 * factorial(4 - 1)`
        - `if false then 1 else 4 * factorial(4 - 1)`
        - `4 * factorial(4 - 1)`
        - `4 * factorial(3)`
        - `4 * (if 3 == 0 then 1 else 3 * factorial(3 - 1))`
        - `4 * (if false then 1 else 3 * factorial(3 - 1))`
        - `4 * (3 * factorial(3 - 1))`
        - `4 * (3 * factorial(2))`
        - `4 * (3 * (if 2 == 0 then 1 else 2 * factorial(2 - 1)))`
        - `4 * (3 * (if false then 1 else 2 * factorial(2 - 1)))`
        - `4 * (3 * (2 * factorial(2 - 1)))`
        - `4 * (3 * (2 * factorial(1)))`
        - `4 * (3 * (2 * (if 1 == 0 then 1 else 1 * factorial(1 - 1))))`
        - `4 * (3 * (2 * (if false then 1 else 1 * factorial(1 - 1))))`
        - `4 * (3 * (2 * (1 * factorial(1 - 1))))`
        - `4 * (3 * (2 * (1 *factorial(0))))`
        - `4 * (3 * (2 * (1 * (if 0 == 0 then 1 else factorial(0 - 1)))))`
        - `4 * (3 * (2 * (1 * (if true then 1 else factorial(0 - 1)))))`
        - `4 * (3 * (2 * (1 * (1))))`
        - `4 * (3 * (2 * (1 * 1)))`
        - `4 * (3 * (2 * (1)))`
        - `4 * (3 * (2 * 1))`
        - `4 * (3 * (2))`
        - `4 * (3 * 2)`
        - `4 * (6)`
        - `4 * 6`
        - `24`
        - > I went a _bit_ overkill, but it was fun XD
    - Example 1 was relatively flat as compared to example 2.
    - This is because `gcd` calls itself as it's last action, whereas `factorial` still has to do some work on the recursive answer.
- Tail recursion
    - If a function calls itself as its last action, its called a _tail recursion_
    - For such functions, the stack frame can be reused
    - They are work like a while loop
    - They are more performant (as a single stack frame is used) and there is no(or atleast its very low) possiblity of a stack-overflow.
    - They are also more optimised in scala
    - We can require a function to tail recursive like this:
    ```scala
    import scala.annotation.tailrec

    @tailrec
    def gcd(a: Int, b: Int): Int = if b == 0 then a else gcd(b, a%b)
    ```
    - The function is now required to be tail-recursive