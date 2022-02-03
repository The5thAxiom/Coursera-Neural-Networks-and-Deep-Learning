// :load <filename> in scala3 REPL

def abs(x: Double): Double = if x >= 0 then x else -x
def square(x: Double) = x * x
// below is the Newton's square root function as given in the lecture
/*
it is not that good for small numbers because the differences get 
   very very small as the numbers approach 0.001 (the minimum value for the 'isgoodEnough' function)
   (if the number is less than 0.001, any difference(and the product afterwards) we take is bound to be less than 0.001 as well)
    */
def improve(guess: Double, x: Double) = (guess + x/guess) / 2
def isGoodEnough(guess: Double, x:Double) = abs(guess * guess - x) < 0.001
def sqrtIter(guess: Double, x: Double): Double = 
        if isGoodEnough(guess, x) then guess
        else sqrtIter(improve(guess, x), x)
def sqrt(x: Double) = sqrtIter(1.0, x)

// new sqrt

def newIsGoodEnough(guess: Double, x: Double): Boolean = abs(guess * guess - x) < x/1000
def newSqrtIter(guess: Double, x: Double): Double = 
        if newIsGoodEnough(guess, x) then guess
        else sqrtIter(improve(guess, x), x)
def newSqrt(x: Double) = sqrtIter(1.0, x)

// this didn't really work differently :(
// idk what's happening, i'll come back to this late i think