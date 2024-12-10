package com.steeplesoft.kmp.bootstrap.network

import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

actual fun getClientEngineFactory(): HttpClientEngineFactory<*> = OkHttp
