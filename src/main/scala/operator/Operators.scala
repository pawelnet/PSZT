package operator

import operator.crossover.Crossover
import operator.mutate.Mutate
import operator.select.Select
import operator.stopcase.Stopcase

case class Operators(crossoverOp: Crossover,
                     mutateOp: Mutate,
                     reproduceOp: Select,
                     successOp: Select,
                     stopcaseOp: Stopcase) {
}
