package jruby.collection

import java.{util => ju}
import org.jruby.runtime.builtin.IRubyObject
import org.jruby.javasupport.JavaUtil
import org.jruby.Ruby

/**
 * Created with IntelliJ IDEA.
 * User: arturas
 * Date: 5/17/12
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */

object Utils {
  /**
   * Wraps object into scala collection wrappers if it is a ruby collection.
   *
   * @param obj
   * @tparam T
   * @return
   */
  def wrapRubyCollection[T](obj: Any): T = (obj match {
    case l: ju.List[_] =>
      new ListWrapper[AnyRef](l.asInstanceOf[ju.List[AnyRef]])
    case m: ju.Map[_, _] =>
      new MapWrapper[AnyRef, AnyRef](m.asInstanceOf[ju.Map[AnyRef, AnyRef]])
    case o: IRubyObject if (SetWrapper.isRubySet(o)) =>
      new SetWrapper[AnyRef](o)
    case x: Any => x
    case null => null
  }).asInstanceOf[T]

  /**
   * This is needed, because from JRuby Java::scala.None is actually something
   * different than None obtained from Scala in such way.
   *
   * >> Java::scala.None
   * => Java::Scala::None
   * >> Java::spacemule.helpers.JRuby.None
   * => #<#<Class:0x10060d664>:0x69a54c>
   */
  val None = scala.None

  implicit def extendIRubyObject(obj: IRubyObject) =
    new ExtendedIRubyObject(obj)

  implicit def any2Rb(any: Any, ruby: Ruby) =
    JavaUtil.convertJavaToRuby(ruby, any)
}

class ExtendedIRubyObject(obj: IRubyObject) {
  def call(method: String) =
    obj.callMethod(obj.getRuntime.getCurrentContext, method)

  def call(method: String, arg: Any) =
    obj.callMethod(
      obj.getRuntime.getCurrentContext, method,
      Utils.any2Rb(arg, obj.getRuntime)
    )

  def unwrap[T]: T = obj.toJava(classOf[AnyRef]).asInstanceOf[T]
}
