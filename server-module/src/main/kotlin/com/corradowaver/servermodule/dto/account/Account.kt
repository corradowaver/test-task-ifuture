package com.corradowaver.servermodule.dto.account

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "accounts")
data class Account(
  @Id
  val id: Int,
  var amount: Long?
)
