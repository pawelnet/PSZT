package solver

import task.Task

trait Solver {
  def solve[T](task: Task[T]): List[T]
}
