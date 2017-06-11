package webServer.model

import pszt.eventBus.EventBus.Iteration
import solution.Solution
import util.Types.Population
case class FenotypeSnap(fenotype:List[List[Double]],fitness:Double)
case class IterationResponse(iter:Double,best:String,population: List[FenotypeSnap])



