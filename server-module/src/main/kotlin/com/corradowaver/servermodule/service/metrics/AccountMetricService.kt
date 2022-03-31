package com.corradowaver.servermodule.service.metrics

import com.corradowaver.servermodule.dto.metrics.AccountMetrics
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.LinkedList


const val GET_AMOUNT_COUNTER = "amount.get"
const val ADD_AMOUNT_COUNTER = "amount.add"

@Service
@EnableScheduling
class AccountMetricService(
  val registry: MeterRegistry,
) {

  private val getCounter: Counter
  private val addCounter: Counter
  private val requestsRateQueue = LinkedList<Int>()
  private var requestsPerSecond = 0

  init {
    getCounter = createCounter(GET_AMOUNT_COUNTER)
    addCounter = createCounter(ADD_AMOUNT_COUNTER)
  }

  fun getAccountServiceMetrics(): AccountMetrics {
    val getCounter = registry.find(GET_AMOUNT_COUNTER).counter()?.count()
      ?: throw NoSuchElementException("No counter found for $GET_AMOUNT_COUNTER")
    val addCounter = registry.find(ADD_AMOUNT_COUNTER).counter()?.count()
      ?: throw NoSuchElementException("No counter found for $ADD_AMOUNT_COUNTER")
    return AccountMetrics(getCounter, addCounter)
  }

  fun resetAccountServiceMetrics() {
    registry.remove(addCounter)
    registry.remove(getCounter)
    createCounter(GET_AMOUNT_COUNTER)
    createCounter(ADD_AMOUNT_COUNTER)
  }

  fun getRequestsRate() = requestsRateQueue

  fun increaseGetCounter() {
    registry.counter(GET_AMOUNT_COUNTER).increment()
    requestsPerSecond += 1
  }

  fun increaseAddCounter() {
    registry.counter(ADD_AMOUNT_COUNTER).increment()
    requestsPerSecond += 1
  }

  @Scheduled(fixedDelay = 1000)
  private fun updateRequestsPerSecond() {
    requestsRateQueue.add(requestsPerSecond)
    if (requestsRateQueue.size > 100) {
      requestsRateQueue.poll()
    }
    requestsPerSecond = 0
  }

  private fun createCounter(counterName: String) =
    Counter
      .builder(counterName)
      .register(registry)

}
