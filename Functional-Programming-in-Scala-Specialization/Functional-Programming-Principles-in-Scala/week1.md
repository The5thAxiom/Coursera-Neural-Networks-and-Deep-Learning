# Getting Started
## Coures Introduction
- Scala is very popular in the functional programming world.
- Works well with OOP stuff (as it comiples to JVM iirc)

## Working on Assignments
- Always take the graded assignments (which don't say 'practice' or 'audit version')

## Tools Setup
- jvm and sbt (scala build tool)
- i have the jdk
- and reinstalled sbt
- this is the worst part of starting any new programming language, the complete hell of setting it up
- use powershell to use sbt (i think git bash is causing some issues)

# Functions & Evaluation
## Programming Paradigms
- Scala is a functional programming language
- Forget what you know and jump into functional programming head-on
## Elements of Programming
- Scala REPL (read, eval, print loop) is started by typing `sbt console` (`scala` works if that is installed but i only have sbt rn)
- Types:
    - Scala types are the same as java but start with caps:
        - Byte
        - Short
        - Int
        - Long
        - Float
        - Double
        - Char
        - Boolean
    - Types given after the name, eg: `val x: Int = 5`
- Substitution model: 
    - A way of evaluating expressions by substituting values.
    - Can be applied to any pure function(no side effects)
    - Formalised in $\lambda$-calculus
    - Every expression can't be reduced to a value, eg: `def loop: Int = loop`
    - Two ways:
        - Call by value: evaluate function arguments first
        - Call by name(cue song): function evaluated first
    - Both ways reduce to the same value iff:
        - both expressions consit of pure functions
        - both evaluations terminate

## Evaluation Strategies and Termination
- If the CBV evaluation of an expression terminates, then the CBN evaluation will also terminate (not true for the opposite direction).
- Eg: 
    ```scala
    def first(x: Int, y: Int) = x
    def loop: Int = loop
    def frist(1, loop) //CBN terminates, CBV does not
    ```
- Scala normally uses CBV because:
    - Saves time (sometimes reduces exponentially many evaluations into 1)
    - If a function has side-effects, CBV is more predictable
- Using CBN in scala: `def constOne(x: Int, y: => Int) = x`
- Evaluation of above eg:
    - `constOne(1 + 2, loop)`
        - constOne(1 + 2, loop)
        - constOne(3, loop)
        - 3 (how does this terminate!?!?)
    - `constOne(loop, 1 + 2)`
        - constOne(loop, 1 + 2)
        - constOne(loop, 1 + 2)
        - this goes on forever

## Conditionals and Value Definitions