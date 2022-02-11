# Types and Pattern Matching
## Decomposition
- Say, we are making an interpreter for arithmetic expressions(only numbers and addition)
- Then, we can have a class heirarchy with the trait `Expr` with two subclasses `Number` and `Sum`
- A possible implementation:
    ```scala
    trait Expr:
        def isNumber: Boolean
        def isSum: Boolean
        def numValue: Int
        def leftOp: Expr
        def rightOp: Expr
    end Expr

    class Number(n: Int) extends Expr:
        def isNumber = true
        def isSum = false
        def numValue = n
        def leftOp = throw Error("Number.leftOp")
        def rightOp = throw Error("Number.rightOp")
    end Number

    class Sum(e1: Expr, e2: Expr) extends Expr:
        def isNumber = false
        def isSum = true
        def numValue = throw Error("Sum.numValue")
        def leftOp = e1
        def rightOp = e2
    end Sum
    ```
- Now, we can right an evaluation function:
    ```scala
    def eval(e: Expr): Int =
        if e.isNumber then e.numValue
        else if e.isSum then eval(e.leftOp) + eval(e.rightOp)
        else throw Error("Unknown expression " + e)
    end eval
    ```
- Writing functions with classification and accessing can become tedius (there can be wayy more subclasses (how many will you check))
- There is no guarantee that the evaluation will occur without exceptions
- If we add:
    ```scala
    class Prod(e1: Expr, e2: Expr) extends Expr // for products like `e1 * d2`
    class Var(x: string) extends Expr // for variables
    ```
- Now, for this new set of classes we need to add a bunch more conditions to the `eval` method. We also need 2 new methods in expr (`isVar` and `isProd`), a method to return the name (`varName`) maybe 2 more operations (for products).
- One way to solve the problem would be using `isInstanceOf[T]: Boolean` and `asInstanceOf[T]` (type tests and typecasts), but they are not encouraged
- One solution is _Object Oriented Decomposition_:
    - If all we want is to evaluate expressions
        ```scala
        trait Expr:
            def eval: Int
        
        class Number(n: Int) extends Expr:
            def eval: Int = n

        class Sum(e1: Expr, e2: Expr) extends Expr:
            def eval: Int = e1.eval + e2.eval
        ```
    - But, if we need to display expressions, we need new methods in each class
    - OOD mixes data with the operations on data, it can be good if what we want is data abstraction and encapsulation but can get complex.
    - Adding new data is easy but adding new functionality gets tough
    - OOD will work well if operations are on single objects but not otherwise

## Pattern Matching
- Problem: to find a general way to access heterogenous data in a class heirarchy.
- Now, we will use _Functional Decomposition with Pattern Matching_
- Case Classes:
    - We need to find out which subclass was used and what were the arguments of the constructor
    - This can be done using case classes:
        ```scala
        trait Expr
        case class Number(n: Int) extends Expr
        case class Number(e1: Expr, e2: Expr) extends Expr
        ```
- Pattern matching:
    - A generalisation of the switch case thing
    - Eg:
        ```scala
        def eval(e: Expr): Int = e match
            case Number(n) => n
            case Sum(e1, e2) => eval(e1) + eval(e2)
        ```
    - Match syntax:
        - `match` is preceeded by a selector (the thing which will be matched) and is followed by the cases: `pattern => expression`
    - A `MatchError` is thrown if nonw is found
    - Patterns:
        - constructors
        - variables (starting with a small letter)
        - wildcards (`_` means anything, )
        - constants (1, `true`, `false`, `null`, all else begin with capital letters)
        - type tests (x: Number)
    - Evaluation:
        - `e match {case p1 => e1 ... case pn => en}`
        - match `e` with the patterns `p1`, ... `pn` in the order they are written
        - the whole match expression is then rewritten to the right hand side of the first case where the pattern matches
        - references to pattern variables are replaced by the corresponding parts in the selectors
- `Expr.scala`

## Lists
- Recursive (cons-list), immutable and homogenous data structure
- eg: `val nums: List[Int] = List(1, 2, 3, 4)`, `val empty: List[Nothing] = List()`
- Constructors:
    - `Nil` and `::` (cons)
    - `x :: (y :: Nil)` is `List(x, y)`
    - `nums = 1 :: (2 :: (3 :: (4 :: Nil)))`
    - Any operator that ends with a `:` associates to the right (`a :: b :: c` is interpreted as `a :: (b :: c)`)
    - thus, `nums = 1 :: 2 :: 3 :: 4 :: Nil` is valid!!
- Fundamental methods:
    - `List[T].head: T`: the first element (an empty list has no head (an exception will be thrown))
    - `List[T].tail: List[T]`: the list of all the others
    - `List[T].isEmpty: Boolean`: is the list empty or not
- Patterns:
    - Instead of using methods, we can decompose lists using patter matching (like haskell does!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! omg i love scala)
    - `Nil` is the empty list, `[]` of haskell
    - `x :: y :: xs` is the haskell `x:y:xs`!
    - The pattern `x :: y :: List(xs, ys) :: zs` is a list of length atleast 3 (ys and zs could be Nil)
- `isort.scala`

## Enums
- Algebraic data types are(or atleast of one kind) class heirarchies with only case classes(without methods) (the Expr class above was kinda this)
- Scala has some specific syntax for these ADTs:
    - Expr:
        ```scala
        enum Expr:
            case Var(s: String)
            case Number(n: Int)
            case Sum(e1: Expr, e2: Expr)
            case Prod(e1: Expr, e2: Expr)
        ```
    - Enums can be even simpler like:
        ```scala
        enum Colour:
            case Red
            case Green
            case Blue
        ```
    - Or even:
        ```scala
        enum Element:
            case Fire, Water, Air, Earth, Sky
        ```
    - Now we can use this for pattern matching:
        ```scala
        def isEvil(e: Element) = e match
            case Element.Fire | Element.Sky => True
            case _ => false
        ```
    - We could even import the `Element` enum for a simpler:
        ```scala
        import Element.*
        def isEvil(e: Element) = e match
            case Fire | Sky => True
            case _ => false
        ```
- Parameters and methods:
    ```scala
    enum Direction(val dx: Int, val dy: Int):
        case Right extends Direction(1, 0)
        case Up extends Direction(0, 1)
        case Left extends Direction(-1, 0)
        case Down extends Direction(0, -1)

        def leftTurn = Direction.values((ordinal + 1) % 4)
    ```
- `ordinal`: 
    - The sequence of the case, `Right.ordinal` would be 0, Left's would be 2 ...
    - case => Int (kinda)
- `values`:
    - Takes an int and gives the case with the given ordinal number (the opposite of ordinal)
    - Int => case (kinda)
    - An immutable array in the companion object of an enum that contains the values
    - Only simple cases are in `values`(or have an ordinal), parametrized cases do not
- The above `Direction` enum is expanded by the compiler to:
    ```scala
    abstract class Direction(val dx: Int, val dy: Int):
        def leftTurn = Direction.values((ordinal + 1) % 4)
    end Direction
    object Direction:
        val Right = new Direction(1, 0) {}
        val Up = new Direction(0, 1) {}
        val Left = new Direction(-1, 0) {}
        val Down = new Direction(0, -1) {}
    end Direction
    // values and valueOf are defined in the companion object, and ordinal is defined in the class (all by the compiler)
    ```
- Domain Modelling:
    - ADTs and enums are used when many types (without operations) are needed
    - Eg:
        ```scala
        enum PaymentMethod:
            case CreditCard(kind: Card, holder: String, number: Long, expires: Date)
            case PayPal(email: String)
            case Cash
        ```
- Enums are usually used for pure data, where the operations on such data are defined elsewhere