val nums = 5 :: 4 :: 2 :: 3 :: 1 :: 7 :: 6 :: Nil

def insert(x: Int, xs: List[Int]): List[Int] = xs match
    case Nil => x :: Nil
    case y :: ys =>
        if x < y then x :: y :: ys
        else y :: insert(x, ys)

def isort(xs: List[Int]): List[Int] = xs match
    case Nil => Nil
    case y :: ys => insert(y, isort(ys))

/*
same as the haskell code:

insert :: Integer -> [Integer] -> [Integer]
insert x [] = x : []
insert x (y:ys)
    | x < y = x:y:ys
    | otherwise = y:(insert x ys)

isort :: [Integer] -> [Integer]
isort [] = []
isort (y:ys) = insert y (isort ys)
*/

val numsSorted = isort(nums)