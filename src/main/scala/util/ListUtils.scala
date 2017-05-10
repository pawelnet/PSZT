package util

import scala.collection.mutable.ArrayBuffer

object ListUtils {
  def swap[T](elements: List[T], i: Int, j:Int): List[T] = {
    if (i == j || j < 0 || i < 0) elements
    else {
      val mutableElements = new ArrayBuffer[T] ++= elements

      val tmp = mutableElements(i)
      mutableElements(i) = mutableElements(j)
      mutableElements(j) = tmp

      mutableElements.toList
    }
  }

  def swap[T](elements: List[T], x: T, y: T): List[T] = swap(elements, elements.indexOf(x), elements.indexOf(y))
}
