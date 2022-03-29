package com.corradowaver.servermodule

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServerModuleApplication

fun main(args: Array<String>) {
  runApplication<ServerModuleApplication>(*args)
}
