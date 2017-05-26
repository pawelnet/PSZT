package pszt.eventBus

import pszt.eventBus.EventBus
import pszt.eventBus.EventBus.consoleMessages


object ConsoleBus {
  EventBus.getMessageObservable() subscribe (println(_))
}
