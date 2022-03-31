package com.corradowaver.servermodule.controller

import com.corradowaver.servermodule.service.metrics.AccountMetricService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MetricsController(
  val metricService: AccountMetricService
) {

  @GetMapping("/metrics/reset")
  fun resetCounter() =
    metricService.resetAccountServiceMetrics()

  @GetMapping("/metrics/get")
  fun getAccountServiceMetrics() =
    metricService.getAccountServiceMetrics()

  @GetMapping("/metrics/rate")
  fun getAccountServiceRequestsRate() =
    metricService.getRequestsRate()

}
