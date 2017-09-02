package freestylemacro
package test

import cats._
import cats.implicits._

import freestyle._

import org.scalatest._


class UnitMacroTest extends FunSuite with Matchers {

  @free trait Algebra{
    def f(a: String): FS[String]
    def g(b: Int): FS[Int]
    def h(c: Double)(d: String): FS[(Double, String)]
  }

  @Unit
  implicit val algebraHandler: Algebra.Handler[Id] = new Algebra.Handler[Id]{
    override def f(a: String): Id[String] = a.toLowerCase
    override def g(b: Int): Id[Int] = b - 1
    override def h(c: Double)(d: String): Id[(Double, String)] = (c, d)
  }

  type TestEither[A] = Either[String, A]

  @Unit
  implicit val algebraHandlerE: Algebra.Handler[TestEither] = new Algebra.Handler[TestEither]{
    override def f(a: String): TestEither[String] = Right(a.toLowerCase)
    override def g(b: Int): TestEither[Int] = Right(b - 1)
    override def h(c: Double)(d: String): TestEither[(Double, String)] = Left("nope")
  }

  test("@Unit should create expected functions"){
    """AlgebraHandler.f("A")""" should compile
    """AlgebraHandler.g(1)""" should compile
    """AlgebraHandler.h(0.0)("A")""" should compile
  }

  test("@Unit's functions should have expected functionality"){
    AlgebraHandler.f("A") shouldEqual "a"
    AlgebraHandler.g(1) shouldEqual 0
    AlgebraHandler.h(0.0)("A") shouldEqual (0.0, "A")
  }

  test("@Unit should maintain the monadic context of a handler"){
    AlgebraHandlerE.f("A") shouldEqual Right("a")
    AlgebraHandlerE.g(1) shouldEqual Right(0)
    AlgebraHandlerE.h(0.0)("A") shouldEqual Left("nope")
  }
  
}
