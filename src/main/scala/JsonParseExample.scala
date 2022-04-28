import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object JsonParseExample extends App{
  sealed trait Foo
  case class Bar(xs: Seq[String]) extends Foo
  case class Qux(someString:String, i: Int, d: Double) extends Foo

//  val foo: Foo = Qux("Sasdsd", 13, 14.0)
  val foo:Foo = Bar(Seq("asdd", "Asdasd"))

  val json:String = foo.asJson.spaces2
  println(json)

  val decodedFoo  = decode[Foo](json)
  println(decodedFoo)
  decodedFoo match {
    case Left(error) => println("ERROR")
    case Right(json) =>
      json match {
        case Bar(xs) => println("BAR")
        case Qux(someString, i, d) => println("QUX")
      }
  }
}
