package com.corradowaver.servermodule.repository

import com.corradowaver.servermodule.dto.account.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository
import java.util.Optional
import javax.persistence.LockModeType.PESSIMISTIC_READ
import javax.persistence.LockModeType.PESSIMISTIC_WRITE

@Repository
interface AccountRepository : JpaRepository<Account, Int> {

  @Lock(PESSIMISTIC_READ)
  override fun findById(id: Int): Optional<Account>

  @Lock(PESSIMISTIC_WRITE)
  override fun <S : Account?> save(entity: S): S
}
