package com.corradowaver.servermodule.controller

import com.corradowaver.servermodule.dto.account.Account
import com.corradowaver.servermodule.service.account.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
  val accountService: AccountService
) {

  @GetMapping("/account/{id}")
  fun getAccountAmount(@PathVariable id: Int) =
    accountService.getAmount(id)

  @PostMapping("/account")
  fun addAccountAmount(@RequestBody account: Account) =
    accountService.addAmount(account.id, account.amount ?: throw RuntimeException("todo cannot be null"))
}
