// basic functions needed
def id(x: Int): Int = x
def cube(x: Int): Int = x * x * x
def factorial(x: Int): Int = if x == 0 then 1 else x * factorial(x)

// curried sum
def sum(f: Int => Int)(a: Int, b: Int): Int =
    if a > b then 0 else f(a) + sum(f)(a + 1, b)

// curried product
def product(f: Int => Int)(a: Int, b: Int): Int =
    if a > b then 1 else f(a) * product(f)(a + 1, b)

// factorial in terms of the product function
def factorial2(x: Int): Int = product(id)(1, x)

// general function for both sum and product
def general(f: (Int, Int) => Int, identitiyElement: Int)(g: Int => Int)(a: Int, b: Int): Int =
    if a > b then identitiyElement else f(g(a), general(f, identitiyElement)(g)(a + 1, b))

def product2(f: Int => Int) = general((x, y) => x * y, 1)(f)

// factorial in terms of this general function
def factorial3(x: Int): Int = general((a, b) => a * b, 1)(id)

// OMG THIS WORKED!!!!!!

// instructor's version of the general function:

def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int) =
    def recur(a: Int): Int =
        if a > b then zero
        else combine(f(a), recur(a + 1))
    recur(a)