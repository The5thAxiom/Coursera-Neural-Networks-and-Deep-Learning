class OldRationalNumber(x: Int, y: Int):
    def num = x
    def den = y

def addOldRationalNumber(x: OldRationalNumber, y: OldRationalNumber): OldRationalNumber =
    OldRationalNumber(x.num * y.den + y.num * x.den, x.den * y.den)

def multiplyOldRationalNumber(x: OldRationalNumber, y: OldRationalNumber): OldRationalNumber =
    OldRationalNumber(x.num * y.num, x.den * y.den)

def makeString(x: OldRationalNumber): String =
    s"${x.num}/${x.den}"

class RatNum(x: Int, y: Int):
    def num: Int = x
    def den: Int = y
    def add(a: RatNum): RatNum =
        RatNum(this.num * a.den + a.num * this.den, this.den * a.den)
    def subtract(a: RatNum): RatNum=
        RatNum(this.num * a.den - a.num * den, this.den * a.den)
    def multiply(a: RatNum): RatNum =
        RatNum(this.num * a.num, this.den * a.den)
    def divide(a: RatNum): RatNum =
        RatNum(this.num * a.den, this.den * a.num)
    def neg: RatNum =
        RatNum(-this.num, this.den)
    def reciprocal: RatNum =
        RatNum(this.den, this.num)
    def +(a: RatNum): RatNum =
        add(a)
    def -(a: RatNum): RatNum =
        subtract(a)
    def *(a: RatNum): RatNum =
        multiply(a)
    def /(a: RatNum): RatNum =
        divide(a)
    def unary_-(): RatNum = this.neg
    def < (a: RatNum): Boolean =
        this.num * a.den < this.den * a.num
    def > (a: RatNum): Boolean =
        this.num * a.den > this.den * a.num
    def == (a: RatNum): Boolean =
        this.num * a.den == this.den * a.num
    def != (a: RatNum): Boolean =
        this.num * a.den != this.den * a.num
    def <= (a: RatNum): Boolean =
        this.num * a.den <= this.den * a.num
    def >= (a: RatNum): Boolean =
        this.num * a.den >= this.den * a.num
    override def toString = s"${this.num}/${this.den}"

