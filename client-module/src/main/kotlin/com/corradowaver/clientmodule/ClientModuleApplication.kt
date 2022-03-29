package com.corradowaver.clientmodule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ClientModuleApplication

fun main(args: Array<String>) {
  runApplication<ClientModuleApplication>(*args)
}
