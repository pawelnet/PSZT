package pszt.eventBus


object ConsoleBus {
  EventBus.messageObservable subscribe (println(_))
}
