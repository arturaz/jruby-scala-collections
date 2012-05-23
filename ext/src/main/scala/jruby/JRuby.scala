package jruby

import collection.{SetWrapper, MapWrapper, ListWrapper}
import org.jruby.javasupport.JavaUtil
import org.jruby.runtime.builtin.IRubyObject
import org.jruby.{RubySymbol, Ruby}
import java.{util => ju}

/**
 * Created with IntelliJ IDEA.
 * User: arturas
 * Date: 5/16/12
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */

object JRuby {
  implicit def extendIRubyObject(obj: IRubyObject) =
    new IRubyObjectExtensions(obj)
//  implicit def int2Rb(int: Int) = JavaUtil.convertJavaToRuby(ruby, int)
//  implicit def long2Rb(long: Long) = JavaUtil.convertJavaToRuby(ruby, long)
  implicit def any2Rb(any: Any, ruby: Ruby) =
    JavaUtil.convertJavaToRuby(ruby, any)
//  implicit def sym2rbSym(symbol: Symbol) =
//    RubySymbol.newSymbol(ruby, symbol.name)
}
