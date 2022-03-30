package com.corradowaver.servermodule.repository

import com.corradowaver.servermodule.dto.account.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Int>
