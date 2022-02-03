import scala.annotation.tailrec

def factorial(x: Int): Int =
    @tailrec
    def facto(x: Int, fact: Int): Int = if x == 0 then fact else facto(x - 1, x * fact)
    facto(x, 1)
