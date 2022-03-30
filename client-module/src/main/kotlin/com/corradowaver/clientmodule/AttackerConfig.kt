package com.corradowaver.clientmodule

import java.util.Properties

class AttackerConfig(
  var rCount: Int = 0,
  var wCount: Int = 0,
  var idList: List<Int> = emptyList()
) {
  init {
    val props = Properties()
    props.load(this::class.java.classLoader.getResource("config.properties").readText().byteInputStream())
    rCount = props.getProperty("rCount").toInt()
    wCount = props.getProperty("wCount").toInt()
    idList = props.getProperty("idList").split(",").map { it.toInt() }
  }
}
