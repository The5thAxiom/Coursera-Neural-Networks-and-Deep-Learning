def nth[T](xs: List[T], n: Int): T =
    if n > xs.size - 1 then throw IndexOutOfBoundsException()
    else 
        def iter(l: List[T], i: Int): T =
            if i == n then l.head
            else iter(l.tail, i + 1)
        iter(xs, 0)
end nth

// Martin's solution
def NTH[T](xs: List[T], n: Int): T =
    if xs.isEmpty then throw IndexOutOfBoundsException()
    else if n == 0 then xs.head
    else NTH(xs.tail, n - 1)
end NTH