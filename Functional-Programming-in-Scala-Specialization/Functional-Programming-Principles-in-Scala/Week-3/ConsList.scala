package sam

trait List[T]:
    def isEmpty: Boolean
    def head: T
    def tail: List[T]
end List

class Cons[T](val head: T, val tail: List[T]) extends List[T]:
    def isEmpty = false
end Cons


class Nil[T] extends List[Int]:
    def isEmpty = true
    def head = throw new NoSuchElementException("Nil.head")
    def tail = throw new NoSuchElementException("Nil.tail")