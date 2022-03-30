package com.corradowaver.servermodule.service

import com.corradowaver.servermodule.dto.account.Account
import com.corradowaver.servermodule.repository.AccountRepository
import com.corradowaver.servermodule.service.account.AccountServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.Optional

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class AccountServiceImplTest {

  private val accountRepository = mock(AccountRepository::class.java)
  private val accountService = AccountServiceImpl(accountRepository)

  private val account1 = Account(1, 10)
  private val untouchedAccount = Account(1, null)

  @Test
  fun `account service must return valid value for existing account and cache it`() {
    `when`(accountRepository.findById(1)).thenReturn(Optional.of(account1))
    val result = accountService.getAmount(1)
    assertEquals(account1.amount, result)
    verify(accountRepository, times(1)).findById(1)
    //TODO: caching test
  }

  @Test
  fun `account service must return zero as an account amount if amount is null`() {
    `when`(accountRepository.findById(1)).thenReturn(Optional.of(untouchedAccount))
    val result = accountService.getAmount(1)
    assertEquals(0, result)
  }

  @Test
  fun `account service must throw exception if account was not found`() {
    `when`(accountRepository.findById(1)).thenReturn(Optional.empty())
    assertThrows<IllegalArgumentException> {
      accountService.getAmount(1)
    }
  }

  @Test
  fun `account service must add amount if amount was set before`() {
    `when`(accountRepository.findById(1)).thenReturn(Optional.of(account1))
    `when`(accountRepository.save(any())).thenReturn(any())
    accountService.addAmount(account1.id, 1)
    verify(accountRepository).save(argThat { account ->
      assertEquals(account.amount, 11)
      true
    })
  }

  @Test
  fun `account service must set amount if was not set before`() {
    `when`(accountRepository.findById(1)).thenReturn(Optional.of(untouchedAccount))
    `when`(accountRepository.save(any())).thenReturn(any())
    accountService.addAmount(untouchedAccount.id, 1)
    verify(accountRepository).save(argThat { account ->
      assertEquals(account.amount, 1)
      true
    })
  }
}
