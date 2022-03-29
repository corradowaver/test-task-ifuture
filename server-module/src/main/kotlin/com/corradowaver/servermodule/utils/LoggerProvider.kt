package com.corradowaver.servermodule.utils

import org.slf4j.LoggerFactory

inline fun <reified T:Any> logger() = LoggerFactory.getLogger(T::class.java)
