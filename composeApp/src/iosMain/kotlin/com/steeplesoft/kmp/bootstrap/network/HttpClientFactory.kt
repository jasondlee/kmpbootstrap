package com.steeplesoft.kmp.bootstrap.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

actual fun getClientEngineFactory(): HttpClientEngineFactory<*> = Darwin
