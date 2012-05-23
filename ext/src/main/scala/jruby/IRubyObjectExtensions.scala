package jruby

import _root_.java.{util => ju}
import collection.{MapWrapper, ListWrapper}
import org.jruby.runtime.builtin.IRubyObject
import org.jruby._
import jruby.collection.Utils._

/**
 * Created with IntelliJ IDEA.
 * User: arturas
 * Date: 5/16/12
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */

class IRubyObjectExtensions(obj: IRubyObject) {
  def call(method: String) = obj.call(method)
  def call(method: String, arg: Any) = obj.call(method, arg)

  def asInt: Int = obj match {
    case fn: RubyFixnum => fn.getLongValue.toInt
    case d: RubyFloat => d.getLongValue.toInt
    case bn: RubyBignum => sys.error("Cannot fit "+bn+" to Int!")
    case _ => throw conversionError("Int")
  }

  def asLong: Long = obj match {
    case fn: RubyFixnum => fn.getLongValue
    case d: RubyFloat => d.getLongValue
    case bn: RubyBignum => sys.error("Cannot fit "+bn+" to Long!")
    case _ => throw conversionError("Long")
  }

  def asBigInteger: BigInt = obj match {
    case fn: RubyFixnum => new BigInt(fn.getBigIntegerValue)
    case d: RubyFloat => new BigInt(d.getBigIntegerValue)
    case bn: RubyBignum => new BigInt(bn.getBigIntegerValue)
    case _ => throw conversionError("Bignum")
  }

  def asBoolean: Boolean = obj match {
    case b: RubyBoolean => b.isTrue
    case _ => throw conversionError("Boolean")
  }

  def asFloat: Float = obj match {
    case f: RubyFixnum => f.getDoubleValue.toFloat
    case f: RubyFloat => f.getValue.toFloat
    case _ => throw conversionError("Float")
  }

  def asDouble: Double = obj match {
    case f: RubyFixnum => f.getDoubleValue
    case f: RubyFloat => f.getValue
    case _ => throw conversionError("Double")
  }

  def asSymbol: Symbol = obj match {
    case s: RubySymbol => Symbol(s.toString)
    case _ => throw conversionError("Symbol")
  }

  def asArray[T <: AnyRef]: ListWrapper[T] = obj match {
    case a: RubyArray => new ListWrapper[T](a.asInstanceOf[ju.List[AnyRef]])
    case _ => throw conversionError("Buffer")
  }

  def asMap[K <: AnyRef, V <: AnyRef]: MapWrapper[K, V] = obj match {
    case h: RubyHash =>
      new MapWrapper[K, V](h.asInstanceOf[ju.Map[AnyRef, AnyRef]])
    case _ => throw conversionError("Map")
  }

  def unwrap[T]: T = obj.unwrap[T]

  private[this] def conversionError(className: String): Exception =
    new RuntimeException("Cannot convert %s of class %s to %s!".format(
      obj.inspect().toString, obj.getMetaClass.getRealClass.toString, className
    ))
}
