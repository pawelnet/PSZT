package operator.select

import util.Types.Population

trait Select {
  def apply(population: Population, size: Int, withReplacement: Boolean = false): Population
}
