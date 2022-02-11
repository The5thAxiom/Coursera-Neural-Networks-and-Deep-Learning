trait Expr:
    def eval: Int = this match
        case Number(n: Int) => n
        case Sum(e1: Expr, e2: Expr) => e1.eval + e2.eval
        case Product(e1: Expr, e2: Expr) => e1.eval * e2.eval
    end eval
    def show: String = this match
        case Number(n: Int) => n.toString
        case Sum(e1: Expr, e2: Expr) => s"${e1.show} + ${e2.show}"
        case Product(e1: Expr, e2: Expr) => s"${e1.showP} * ${e2.showP}"
        case Product(e1: Sum, e2: Expr) => s"(${e1.show}) * ${e2.show}"
    end show
    def showP: String = this match
        case e: Sum => s"(${e.show})"
        case _ => this.show
end Expr

case class Number(n: Int) extends Expr
case class Sum(e1: Expr, e2: Expr) extends Expr
case class Var(s: String) extends Expr
case class Product(e1: Expr, e2: Expr) extends Expr

val five = Number(5)
val six = Number(6)
val one = Number(1)

val sum1 = Sum(five, six)
val sum2 = Sum(sum1, one)

val var1 = Var("sam")
val var2 = Var("x")

val pr1 = Product(sum1, five)
val pr2 = Product(one, sum1)
var pr3 = Product(sum2, var1)