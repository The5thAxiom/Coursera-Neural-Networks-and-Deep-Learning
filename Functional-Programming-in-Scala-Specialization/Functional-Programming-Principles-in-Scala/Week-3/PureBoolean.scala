class PureBool extends AnyVal:
    def ifThenElse[T](t: => T, e: => T): T // Call by Name parameters
    infix def &&(x: => PureBool): PureBool = ifThenElse(x, pureFalse)
    infix def ||(x: => PureBool): PureBool = ifThenElse(pureTrue, x)
    def unary_! = ifThenElse(pureFalse, pureTrue)

    infix def ==(x: PureBool): PureBool = ifThenElse(x, x.unary_!)
    infix def !=(x: PureBool): PureBool = ifThenElse(x.unary_!, x)

    infix def ==>(x: PureBool): PureBool = ifThenElse(x, pureTrue) // code doesn't really work but the exercise was to code the ==> operator and I did that right so its fine!
end PureBool

object pureTrue extends PureBool:
end pureTrue

object pureFalse extends PureBool:
end pureFalse
