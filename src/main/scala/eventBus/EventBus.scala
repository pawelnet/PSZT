package pszt.eventBus

import pszt.eventBus.TaskBus.TaskType.TaskType
import rx.lang.scala.Subject
import util.Types.Genotype


object EventBus {

  type Args = Any
  type Task = (TaskType, Args)
  type Iteration = (List[Genotype], Integer, Double)

  private val consoleMessages = Subject[String]()
  private val iterationSubject = Subject[Iteration]()
  private val taskSubject = Subject[Task]()


  def getTaskObservable() = taskSubject asJavaObservable

  def getTaskObserver() = taskSubject asJavaObserver

  def getMessageObservable() = consoleMessages asJavaObservable

  def getIterationObservable() = iterationSubject asJavaObservable

  def getIterationObserver() = iterationSubject asJavaObserver

  def getMessageObserver() = consoleMessages asJavaObserver


}
