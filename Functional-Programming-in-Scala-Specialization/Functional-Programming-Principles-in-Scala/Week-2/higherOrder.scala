def id(x: Int): Int = x
def cube(x: Int): Int = x * x * x
def factorial(x: Int): Int = if x == 0 then 1 else x * factorial(x - 1)
def sum(f: Int => Int, a: Int, b: Int): Int =
    if a > b then 0
    else f(a) + sum(f, a + 1, b)
