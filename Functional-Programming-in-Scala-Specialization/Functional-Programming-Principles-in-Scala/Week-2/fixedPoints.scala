val tolerance = 0.0001

def abs(x: Double): Double = if x >= 0 then x else -x

def isCloseEnough(x: Double, y: Double) =
    abs((x - y)/x) < tolerance

// the first approach
def fixedPoint1(f: Double => Double)(firstGuess: Double): Double =
    def iterate(guess: Double): Double =
        val next = f(guess)
        println(next)
        if isCloseEnough(guess, next) then next
        else iterate(next)
    iterate(firstGuess)

// sqrt
def sqrt1(x: Double) =
    fixedPoint1(y => (y + x/y)/2)(1.0)

// average damping
def averageDamp(f: Double => Double)(x: Double): Double =
    (x + f(x))/2

// using average damping for sqrt
def sqrt(x: Double) = fixedPoint1(averageDamp(y => x/y))(1.0)

// 20220203