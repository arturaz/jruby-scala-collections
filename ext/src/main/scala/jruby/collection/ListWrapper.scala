package jruby.collection

import collection.{JavaConversions => jc}
import java.{util => ju}
import collection.mutable.Buffer

class ListWrapper[T <: AnyRef](raw: ju.List[AnyRef]) extends Buffer[T] {

  class Iterator extends scala.Iterator[T] {
    private[this] lazy val iterator = raw.iterator()

    def hasNext = iterator.hasNext

    def next() = Utils.wrapRubyCollection[T](iterator.next())
  }

  def rubyArray = raw

  def apply(index: Int) = raw.get(index) match {
    case null => throw new NoSuchElementException("unknown index: " + index)
    case x: Any => Utils.wrapRubyCollection[T](x)
  }

  def update(index: Int, newelem: T) {
    raw.set(index, newelem)
  }

  def length = raw.size()

  def +=(elem: T) = {
    raw.add(elem)
    this
  }

  def clear() {
    raw.clear()
  }

  def +=:(elem: T) = {
    raw.add(0, elem)
    this
  }

  def insertAll(n: Int, elems: Traversable[T]) {
    raw.addAll(n, jc.asJavaCollection(elems.toIndexedSeq))
  }

  def remove(n: Int) = Utils.wrapRubyCollection[T](raw.remove(n))

  def iterator = new Iterator
}
