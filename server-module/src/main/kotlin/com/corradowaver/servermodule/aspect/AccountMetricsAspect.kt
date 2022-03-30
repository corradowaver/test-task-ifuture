package com.corradowaver.servermodule.aspect

import com.corradowaver.servermodule.service.metrics.AccountMetricService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.springframework.context.annotation.Configuration

@Aspect
@Configuration
class AccountMetricsAspect(
  val accountMetricService: AccountMetricService
) {

  @After("execution(* com.corradowaver.servermodule.controller.AccountController.getAccountAmount(..))")
  fun incrementGetCounter(joinPoint: JoinPoint) =
    accountMetricService.increaseGetCounter()

  @After("execution(* com.corradowaver.servermodule.controller.AccountController.addAccountAmount(..))")
  fun incrementAddCounter(joinPoint: JoinPoint) =
    accountMetricService.increaseAddCounter()
  
}
