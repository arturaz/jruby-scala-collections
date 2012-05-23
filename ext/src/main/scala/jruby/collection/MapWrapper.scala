package jruby.collection

import java.{util => ju}
import collection.mutable.Map

// Scala-Ruby Hash
class MapWrapper[K <: AnyRef, V <: AnyRef](raw: ju.Map[AnyRef, AnyRef])
  extends Map[K, V] {

  class Iterator extends scala.Iterator[(K, V)] {
    private[this] lazy val iterator = raw.entrySet().iterator()

    def hasNext = iterator.hasNext

    def next() = {
      val entry = iterator.next.asInstanceOf[ju.Map.Entry[AnyRef, AnyRef]]
      (
        Utils.wrapRubyCollection[K](entry.getKey),
        Utils.wrapRubyCollection[V](entry.getValue)
        )
    }
  }

  def rubyHash = raw

  def +=(kv: (K, V)) = {
    raw.put(kv._1, kv._2)
    this
  }

  def -=(key: K) = {
    raw.remove(key)
    this
  }

  override def apply(key: K): V = raw.get(key) match {
    case null => throw new NoSuchElementException("unknown key: " + key)
    case obj: Any => Utils.wrapRubyCollection[V](obj)
  }

  def get(key: K) = raw.get(key) match {
    case null => None
    case obj: Any => Some(Utils.wrapRubyCollection[V](obj))
  }

  override def size = raw.size()

  def iterator = new Iterator

  override def repr = this
}
