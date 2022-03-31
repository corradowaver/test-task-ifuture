package com.corradowaver.servermodule.service.account

import com.corradowaver.servermodule.config.ACCOUNTS_CACHE_NAME
import com.corradowaver.servermodule.repository.AccountRepository
import com.corradowaver.servermodule.utils.logger
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException

@Service
@Transactional
@EnableRetry
class AccountServiceImpl(
  val accountRepository: AccountRepository
) : AccountService {

  private val logger = logger<AccountServiceImpl>()

  /**
   * Retrieves current balance or zero if addAmount() method was not called before for specified id
   * Caches account
   */
  @Cacheable(ACCOUNTS_CACHE_NAME)
  override fun getAmount(id: Int): Long =
    accountRepository
      .findById(id)
      .orElseGet {
        logger.error("Account with such id was not found")
        throw IllegalArgumentException("Account with such id was not found")
      }
      .also {
        logger.info("Account $id was retrieved from database")
      }
      .amount ?: 0

  /**
   * Increases balance or set if addAmount() method was called first time
   * Evicts updated account from cache if exists
   */
  @CacheEvict(ACCOUNTS_CACHE_NAME, key = "#id")
  @Retryable(SQLException::class)
  override fun addAmount(id: Int, value: Long) {
    val account = accountRepository
      .findById(id)
      .orElseGet {
        logger.error("Account with such id was not found")
        throw RuntimeException("Account with such id was not found")
      }
      .apply {
        amount = if (amount != null) {
          amount?.plus(value)
        } else {
          value
        }
      }
    accountRepository.save(account)
    logger.info("Account $id amount was updated")
  }
}
