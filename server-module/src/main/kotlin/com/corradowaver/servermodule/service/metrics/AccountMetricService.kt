package com.corradowaver.servermodule.service.metrics

import com.corradowaver.servermodule.dto.metrics.AccountMetrics
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Service


const val GET_AMOUNT_COUNTER = "amount.get"
const val ADD_AMOUNT_COUNTER = "amount.add"

@Service
class AccountMetricService(
  val registry: MeterRegistry,
) {

  private val getCounter: Counter
  private val addCounter: Counter

  init {
    getCounter = createCounter(GET_AMOUNT_COUNTER)
    addCounter = createCounter(ADD_AMOUNT_COUNTER)
  }

  fun increaseGetCounter() =
    registry.counter(GET_AMOUNT_COUNTER).increment()

  fun increaseAddCounter() =
    registry.counter(ADD_AMOUNT_COUNTER).increment()

  fun resetAccountServiceMetrics() {
    registry.remove(addCounter)
    registry.remove(getCounter)
    createCounter(GET_AMOUNT_COUNTER)
    createCounter(ADD_AMOUNT_COUNTER)
  }

  fun getAccountServiceMetrics(): AccountMetrics {
    val getCounter = registry.find(GET_AMOUNT_COUNTER).counter()?.count()
      ?: throw NoSuchElementException("No counter found for $GET_AMOUNT_COUNTER")
    val addCounter = registry.find(ADD_AMOUNT_COUNTER).counter()?.count()
      ?: throw NoSuchElementException("No counter found for $ADD_AMOUNT_COUNTER")
    return AccountMetrics(getCounter, addCounter)
  }

  private final fun createCounter(counterName: String) =
    Counter
      .builder(counterName)
      .register(registry)

}
