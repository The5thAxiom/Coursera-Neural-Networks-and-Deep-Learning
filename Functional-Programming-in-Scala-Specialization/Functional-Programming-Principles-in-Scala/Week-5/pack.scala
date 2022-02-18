// pack consequtive duplicates into sublists

def pack[T](xs: List[T]): List[List[T]] = xs match
    case Nil => Nil
    case x :: xs1 => List(x :: xs1.takeWhile(y => y == x)) ++ pack(xs1.dropWhile(y => y == x)) // my version

val list = List(1, 1, 1, 2, 1, 1, 2, 3, 4, 5, 5, 6)
val packed = pack(list)

def encode[T](xs: List[T]): List[(T, Int)] = 
    // def acc(ys: List[List[T]]): List[(T, Int)] = ys match
    //     case Nil => Nil
    //     case y :: ys => (y.head, y.size) :: acc(ys)
    // acc(pack(xs))
    // i should've used a map in hindsight
    pack(xs).map(x => (x.head, x.length))

val encoded = encode(list)