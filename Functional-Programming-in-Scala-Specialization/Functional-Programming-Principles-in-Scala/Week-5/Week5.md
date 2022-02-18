# Lists

## A Closer Look at Lists
- Methods: `x: List[A]`, `y: List[A]`, `z: A`
    - `x.head`: first element, O(1)
    - `x.tail`: everything but the first element, O(1)
    - `x.isEmpty`: true if x is empty
    - `x :: y`: cons
    - `x.length`
    - `x.last`: last element, O(n)
    - `x.init`: everything but the last element, O(n)
    - `x.take(n)`: first n elements of x
    - `x.drop(n)`: everything but the first n elements of x
    - `x(n)` or `x.apply(n)`: the element of x at index n
    - `x ++ y`: concatenate, O(n)
    - `x.reverse`
    - `x.updated(n, z)`: replace x(n) with z
    - `x.indexOf(z)`: the index of the first element in x equal to z
    - `x.contains(z)`
    - `x.splitAt(n)`: a pair of (elements upto n, elments after n)

## Tuples and Generic Methods
- Pairs (a tuple of 2 elements):
    - Written in parenthesis, `(x, y)`
    - `val pair: (String, Int) = ("answer", 42)`
    - `val (label, value) = pair`
    - This works the same for any tuple
- Translation of tuples (n <= 22):
    - The type `(T1, ... Tn)` is an abbreviation of `scala.Tuplen[T1, ... Tn]`
    - The expression `(e1, ... en)` is an abbreviation of `scala.Tuplen(e1, ... en)`
    - The pattern `(ep, ... pn)` is an abbreviation of `scala.Tuplen(p1, ... pn)`
- TupleXXL is used for tuples bigger than 22
- Accessors: `pair._1`, `pair._2`, ...

## Higher-Order List Functions
- Mapping:
    - Apply a function to every element of a list and return a new list
    - Definition:
        ```scala
        extension[T](xs: List[T])
            def map[U](f: T => U): List[U] = xs match
                case Nil => xs
                case x :: xs => f(x) :: xs.map(f)
        ```
    - The actual map is more complex(as it works for more than *just* lists)
- Filtering:
    - Return a list of only those elements which satisfy a condition
    - Definition:
        ```scala
        extension[T](xs: List[T])
            def filter(p: T => Boolean): List[T] = xs match
                case Nil => xs
                case x :: xs => if p(x) then x :: xs.filter(p) else xs.filter(p)
        ```
    - Variations of filter:
        - `xs.filterNot(p)`: filter the elements which do not satisfy p
        - `xs.partition(p)`: (xs.filter(p), xs.filterNot(p)) but in 1 pass
        - `xs.takeWhile(p)`: the longest prefix of list xs in which all elements satisfy p
        - `xs.dropWhile(p)`: the remainder of takeWhile
        - `xs.span(p)`: (xs.takeWhile(p), xs.dropWhile(p)) but in 1 pass

## Reduction of Lists
- Reduction:
    - To combine each element by using a binary operator
- `reduceLeft`:
    - `xs.reduceLeft((x , y) => x + y)` means the sum of all elements of xs
    - We can also write: `_ + _` instead of the complete anonymous function
- `foldLeft`:
    - A more general version of reduceLeft
    - Takes an accumulator(the value returned for an empty list)
    - `xs.foldLeft(0)(_ + _)`
    - reduceLeft(f) = foldLeft(xs.head)(f); kinda
- `reduceRight` and `foldRight` also exist, doing the same thing but starting from the end of the list
- If `foldLeft` and `foldRight`, both can be used (for commutative, associative functions) then foldLeft would be more efficient as it is tail recursive.

## Reasoning About Lists
- Structural Induction:
    - I've done this in college, not gonna write details here
    - base case, inductive hypothesis, inductive step
- A pure function doesn't have any side-effects and is thus equivalent to the term it reduces to (aka _referential transparency_)
