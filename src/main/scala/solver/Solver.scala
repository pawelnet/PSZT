package solver

import task.Task

trait Solver {
  def solve[T](task: Task[T]): T
}
