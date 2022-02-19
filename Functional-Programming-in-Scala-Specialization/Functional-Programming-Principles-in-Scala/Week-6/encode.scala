// val mnemonics = Map(
//     '2' -> "ABC",
//     '3' -> "DEF",
//     '4' -> "GHI",
//     '5' -> "JKL",
//     '6' -> "MNO",
//     '7' -> "PQRS",
//     '8' -> "TUV",
//     '9' -> "WXYZ"
// )/* .withDefaultValue("") */

// def encode(phoneNumber: List[Char]): List[String] =
//     val xs = phoneNumber.map(mnemonics(_))
//     x
// end encode

// val a = encode("7225247386".toList)

class Coder(words: List[String]):
    val mnemonics = Map(
        '2' -> "ABC",
        '3' -> "DEF",
        '4' -> "GHI",
        '5' -> "JKL",
        '6' -> "MNO",
        '7' -> "PQRS",
        '8' -> "TUV",
        '9' -> "WXYZ"
    )
    private val charCode: Map[Char, Char] =
        for
            (digit, str) <- mnemonics
            ltr <- str
        yield ltr -> digit

    private def wordCode(word: String): String = word.toUpperCase.map(charCode)

    private val wordsForNum: Map[String, List[String]] = words.groupBy(wordCode).withDefaultValue(Nil)

    def encode(number: String): Set[List[String]] =
        if number.isEmpty then Set(Nil)
        else
            for
                splitPoint <- (1 to number.length).toSet
                word <- wordsForNum(number.take(splitPoint))
                rest <- encode(number.drop(splitPoint))
            yield word :: rest
end Coder

val x = Coder(List("Scala", "Python", "Ruby", "C", "rocks", "socks", "sucks", "works", "pack"))
val y =  x.encode("7225276257")

// omg this works