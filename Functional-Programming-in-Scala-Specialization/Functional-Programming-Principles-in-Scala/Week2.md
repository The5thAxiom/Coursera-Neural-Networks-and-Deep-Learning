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