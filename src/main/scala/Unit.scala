package freestylemacro

import scala.annotation.StaticAnnotation
import scala.meta._

class Unit extends StaticAnnotation{
  /*
   * The "@Unit" annotation will take a handler and place all the functions
   * overriden in the handler and place them in an object with the same
   * name as the handler, except capitalized.  This allows functions defined
   * in the handler to be unit tested
   */
  inline def apply(defn: Any): Any = meta {
    
    val valImplementation = defn.children.last.children.head.children

    val objectName = defn.children.tail.head.syntax.capitalize

    val functions = valImplementation.map{
      case a@q"override def $name(...$paramss): $tp = $body" =>
        Some(
          a.transform{
            case q"override def $name(...$paramss): $tp = $body" =>
              q"def $name(...$paramss): $tp = $body"
          }
        )
      case _ => None
    }.flatten

    val formattedFunctions = functions.map(x => x.syntax.parse[Stat].get)

    q"object ${Term.Name(objectName)} { .. $formattedFunctions }"
  }
}
