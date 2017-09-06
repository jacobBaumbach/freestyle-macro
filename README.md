# freestyle-macro
Macros to make using Freestyle easier

## Macros
 - Unit
 
## Unit
`@Unit` is a macro that allows you to access methods in your handler to perform unit tests on the overriden functions in your given handler.

Imagine you created an algebra called `Algebra`:
```scala
  @free trait Algebra{
    def f(a: String): FS[String]
    def g(b: Int): FS[Int]
    def h(c: Double)(d: String): FS[(Double, String)]
  }
```

Then you create a Handler as such:
```scala
  implicit val algebraHandler: Algebra.Handler[Id] = new Algebra.Handler[Id]{
    override def f(a: String): Id[String] = a.toLowerCase
    override def g(b: Int): Id[Int] = b - 1
    override def h(c: Double)(d: String): Id[(Double, String)] = (c, d)
  }
```

The only way to test the functions you overrode in your handler are correct is by integration test.  

If you would like to unit test your overriden handler functions, simply use the `@Unit` macro as such:
```scala
  @Unit implicit val algebraHandler: Algebra.Handler[Id] = new Algebra.Handler[Id]{
    override def f(a: String): Id[String] = a.toLowerCase
    override def g(b: Int): Id[Int] = b - 1
    override def h(c: Double)(d: String): Id[(Double, String)] = (c, d)
  }
```

Now you can access `f`, `g`, or `h` by simply calling the function via `AlgebraHandler.f`, `AlgebraHandler.g`, or `AlgebraHandler.h`
```scala
  scala> AlgebraHandler.f("A")
  "a"
  scala> AlgebraHandler.g(1)
  0
  scala> AlgebraHandler.h(0.0)("A")
  (0.0, "A")
```

`@Unit` allows you to access these overriden funcitons and now you may perform unit tests instead of solely relying on integration tests, improving the validity of you freestyle applications.
