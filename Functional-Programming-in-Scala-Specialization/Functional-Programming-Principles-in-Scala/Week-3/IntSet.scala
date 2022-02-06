abstract class IntSet:
    def incl(x: Int): IntSet
    def contains(x: Int): Boolean
    def union(other: IntSet): IntSet
end IntSet

object Empty extends IntSet:
    def contains(x: Int): Boolean = false
    def incl(x: Int): IntSet = NonEmtpy(x, Empty, Empty)
    def union(other: IntSet): IntSet = other
end Empty

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet:
    def contains(x: Int): Boolean =
        if x < elem then left.contains(x)
        else if x > elem then right.contains(x)
        else true
    end contains
    
    def incl(x: Int): IntSet =
        if x < elem then NonEmpty(elem, left.incl(x), right)
        else if x > elem then NonEmpty(elem, left, right.incl(x))
        else this
    end incl

    def union(other: IntSet): IntSet =
        // this.left.union(this.right).union(other).incl(this.elem) // this works but is inefficient (couldn't think of this myself )
    end union
end NonEmpty

object IntSet:
    def apply(): IntSet = Empty
    def apply(x: Int): IntSet = Empty.incl(x)
end IntSet