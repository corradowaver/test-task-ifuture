package com.corradowaver.clientmodule

import io.ktor.client.HttpClient
import io.ktor.client.engine.java.Java
import io.ktor.client.request.request
import io.ktor.client.statement.HttpStatement
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll


object AccountAttacker {

  private val client = HttpClient(Java) {
    engine {
      threadsCount = 8
      pipelining = true
      this.pipelining
    }
  }


  suspend fun attack() {

    val config = AttackerConfig()

    awaitAll(
      incAmount(config.wCount, config.idList),
      decAmount(config.wCount, config.idList),
      readAmount(config.rCount, config.idList)
    )

  }


  fun incAmount(wCount: Int, idList: List<Int>) =
    GlobalScope.async {
      repeat(wCount) {
        val id = idList.random()
        val status =
          client.request<HttpStatement>("http://localhost:8080/account") {
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            body = """
              {
                "id" : $id,
                "amount" : 1
              }
            """.trimIndent()
          }.execute().status
      }
    }


  fun decAmount(wCount: Int, idList: List<Int>) =
    GlobalScope.async {
      repeat(wCount) {
        val id = idList.random()
        val status = client.request<HttpStatement>("http://localhost:8080/account") {
          method = HttpMethod.Post
          contentType(ContentType.Application.Json)
          body = """
            {
              "id" : $id,
              "amount" : -1
            }
          """.trimIndent()
        }.execute().status
      }
    }

  fun readAmount(rCount: Int, idList: List<Int>) =
    GlobalScope.async {
      repeat(rCount) {
        val id = idList.random()
        val response = client.request<HttpStatement>("http://localhost:8080/account/$id") {
          method = HttpMethod.Get
        }
        val stringBody: String = response.receive()
      }
    }
}

suspend fun main() {
  AccountAttacker.attack()
}
