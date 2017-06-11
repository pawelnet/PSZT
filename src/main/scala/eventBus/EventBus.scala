package pszt.eventBus

import pszt.eventBus.TaskBus.TaskType.TaskType
import rx.lang.scala.Subject
import solution.Solution
import util.Types.{Genotype, Population}


object EventBus {
  self =>
  type Args = Any
  type Task = (TaskType, Args)
  type Iteration = (Population, Int, Any)

  private val consoleMessages = Subject[String]()
  private val iterationSubject = Subject[Iteration]()
  private val solutionSubject = Subject[(Solution, Any)]()
  private val taskSubject = Subject[Task]()


  def taskObservable = taskSubject asJavaObservable

  def taskObserver = taskSubject asJavaObserver

  def messageObservable = consoleMessages asJavaObservable

  def iterationObservable = iterationSubject asJavaObservable

  def iterationObserver = iterationSubject asJavaObserver

  def solutionObservable = solutionSubject asJavaObservable

  def solutionObserver = solutionSubject asJavaObserver

  def messageObserver = consoleMessages asJavaObserver


}
