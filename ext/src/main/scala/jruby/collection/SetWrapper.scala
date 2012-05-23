package jruby.collection

import collection.mutable.Set
import org.jruby.runtime.builtin.IRubyObject
import Utils._
import org.jruby.exceptions.RaiseException

object SetWrapper {
  def isRubySet(obj: IRubyObject): Boolean =
    obj.getMetaClass.getRealClass == obj.getRuntime.getClass("Set")
}

class SetWrapper[T <: AnyRef](raw: IRubyObject) extends Set[T] {
  if (!SetWrapper.isRubySet(raw))
    throw new IllegalArgumentException(
      "Wrapped IRubyObject set must be Ruby ::Set, but it was ::" +
        raw.getMetaClass.getRealClass.toString
    )

  class Iterator extends scala.Iterator[T] {
    private[this] lazy val iterator = raw.call("each")

    def hasNext = {
      try {
        iterator.call("peek")
        true
      }
      catch {
        case e: RaiseException =>
          if (
            e.getException.getMetaClass.getRealClass ==
              iterator.getRuntime.getClass("StopIteration")
          )
            false
          else
            throw e
      }
    }

    def next() =
      Utils.wrapRubyCollection[T](iterator.call("next").unwrap[AnyRef])
  }

  def rubySet = raw

  def +=(elem: T) = {
    raw.call("add", elem)
    this
  }

  def -=(elem: T) = {
    raw.call("delete", elem)
    this
  }

  def contains(elem: T) = raw.call("include?", elem).unwrap[Boolean]

  def iterator = new Iterator
}
