// my solutions are a ***bit*** hard to read
class Polynom(nonZeroTerms: Map[Int, Double]):
    def this(bindings: (Int, Double)*) = this(bindings.toMap)
    def terms = nonZeroTerms.withDefaultValue(0.0)
    def + (other: Polynom): Polynom = 
        Polynom(for (key, value) <- (this.terms ++ other.terms) yield (key, value + this.terms(key)))
    override def toString = terms.map((key, value) => s"${value}${if key == 0 then "" else s"x^${key}"}").foldLeft("")((x, y) => y ++ " + " ++ x)
end Polynom

val a = Polynom(0 -> 2.0, 2 -> 3.0)

val b = Polynom(0 -> 3.0, 1 -> 1.0, 2 -> 4.0)

val c = a + b

val d = Polynom(Map[Int, Double]())