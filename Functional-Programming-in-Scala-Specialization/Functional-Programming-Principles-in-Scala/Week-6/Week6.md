# Collections

## Other Collections
- Vectors:
    - Constant access time
    - Immutable, persistent data structure
    - `val nums = Vector(1, 2, 3, -88)`
    - Basically the same operations as Lists, except `::`
    - `+:` and `:+` are used to in place of `::` (the `:` points toward the sequence)
    - Eg:
        ```scala
        val nums = Vector(1, 2, 3)
        val nums2 = 0 +: nums // puts 0 at the start of nums
        val nums3 = nums :+ 4 // puts 4 at the end of nums
        ```
- Heirarchy:
    - The common base class of `List` and `Vector` is `Seq`(sequence)
    - `Seq` is a subclass of `Iterable`
- Arrays and Strings:
    - Support the same operations as Seq
    - Can be implicitly converted to Seq
- Ranges:
    - A sequence of evenly space integers
    - Operators:
        - `to`: inclusive
        - `until`: exclusive
        - `by`: the step value
    - Eg:
        ```scala
        val r: Range = 1 to 10 by 2
        val s: Range = 100 until 1 by 3
        ```
    - Represented as a single object with 3 fields: lower bound, upper bound, step value
- More methods of `Seq`:
    - `xs.exists(p)`: if there is an element x of xs such that p(x) holds
    - `xs.forall(p)`: if p(x) holds for all x $\in$ xs
    - `xs.zip(ys)`: a sequence of pairs from corresponding elements of xs and ys
    - `xs.unzip`: the opposite of zip
    - `xs.flatMap(f)`: applies a collection-valued function `f` to all elements of xs and concatenates the results
    - `xs.sum`, `xs.product`: for numeric collections
    - `xs.max`, `xs.min`: an Ordering must exist for the collection
- Eg:
    - All combinations x and y where x $\in$ 1 to M and y $\in$ 1 to N:
        ```scala
        (1 to M).flatMap(x => (1 to N).map(y => (x, y)))
        ```
    - The scalar product of 2 vectors:
        ```scala
        xs.zip(ys).map((x, y) => x * y).sum
        // or, we can decompose the pair ourselves
        xs.zip(ys).map(z => z._1 * z._2).sum
        // or, in a more concise way:
        xs.zip(ys).map(_ * _).sum
        ```
    - Testing if a number is prime:
        ```scala
        def isPrime(n: Int): Boolean = n match
            case 1 => false
            case _ => !(2 until n).exists(x => n % x == 0)
        ```
        Martin's answer:
        ```scala
        def isPrime(n: Int): Boolean = (2 until n).forall(n % _ != 0)
        ```

## Combinatorial Search and For Expressions
- Higher order functions can do the work done by for loops in imperative languages
- For expressions:
    - Assume a class: `case class Person(name: String, age: Int)`, and a Seq persons
    - Now, if we need to get the names of all people over 20 years old, we can do:
        ```scala
        persons.filet(p => p.age > 20).map(p => p.name)
        ```
    - We can use a `for` expression for the same thing:
        ```scala
        for p <- persons if p.age < 20 yield p.name // `<-` is called 'taken from'
        ```
- Syntax of `for`:
    - `for s yield e`
    - `s` is a sequence of generators and filters
    - `e` is an expression whose value is returned by an iteration
- Generators
    - of the form `p <- e`, where p is a pattern and e is an expression whose value is a collection
- Eg:
    - Given n, find all pairs (i, j) such that j + j is prime
        ```scala
        for
            i <- 1 until n
            j <- 1 until i
            if isPrime(i + j)
        yield (i, j)
        ```
    - Scalar product of 2 vectors xs and ys:
        ```scala
        def scalarProduct(xs: List[Double], ys: List[Double]): Double =
            (for (x, y) <- xs.zip(ys) yield x * y).sum
        ```

## Combinatorial Search Example
- Sets
    - A `Seq`, eg: `val nums = Set(1, 2, 3)`
    - Most operations on `Seq` are available for Sets
    - Unordered, cannot have duplicates
    - The fundamental operation on sets: `s.contains(x)`
- N-Queens problem:
    - Place N queens on an $N \times N$ chessboard such that no Queen could capture any other Queen
    - Implementation:
        ```scala
        def queens(n: Int): Set[List[Int]] =
            def checks(col: Int, delta: Int, queens: List[Int]): Boolean = queens match
                case qcol :: others => qcol == col || (qcol - col).abs == delta || checks(col, delta + 1, others)
                case Nil => false
            end checks
            def isSafe(col: Int, queens: List[Int]): Bolean = !checks(col, 1, queens)
            def placeQueens(k: Int): Set[List[Int]] =
                if k == 0 then Set(List())
                else
                    for
                        queens <- placeQueens(k - 1)
                        col <- 0 until n
                        if isSafe(col, queens)
                    yield col :: queens
            end placeQueens
            placeQueens(n)
        ```

## Maps
- Associates keys with values
- eg: `val ages: Map[String, Int] = Map("Sam" -> 19, "Shlok" -> 17)`
- The class `Map[Key, Value]` extends the collection type `Iterable[(Key, Value)]`
- `->` is an alternate way of writing pairs
- Maps can be used as functions: `ages("Sam")` will return 19 (works only if the key exists in the map, `ages("abc")` will throw an exception)
- `ages.get("abc")` would work (and would return None), `get` returns an Option type
- We can decompose the `Option` type:
    ```scala
    def getAge(person: String) = ages.get(person) match
        case Some(x) => x
        case None => -1
    ```
- Updating:
    - `m + (k -> v)`: update/put (k, v) in m
    - `m ++ kvs`: put all kvs in m
- Sorted and GroupBy
    - Example of sortWith and sorted:
        ```scala
        val fruits = List("apple", "pear", "orange")
        fruits.sortWith(_.length < _.length) // arranges the elements by length
        fruits.sorted // does the same
        ```
    - Eg: `fruits.groupBy(_.head)` will create a map with the keys being the heads(first letters) of the words
- `val newAges = ages.withDefaultValue(0)`: newAges returns 0 for any key not in ages
- Example: Polynomials using Maps
    - Mapping exponents to coefficients
    - See `poly.scala`
- Variable length argument lists:
    - see `poly.scala`
    - this was mind blowing, woww

## Putting the Pieces Together
- A function to givea all phrases which can be mnemonics for a given phone number
- see `encode.scala`