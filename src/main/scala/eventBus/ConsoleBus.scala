package pszt.eventBus


object ConsoleBus {
  EventBus.getMessageObservable() subscribe (println(_))
}
