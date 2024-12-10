package com.steeplesoft.kmp.bootstrap.network

import io.ktor.client.engine.HttpClientEngineFactory

expect fun getClientEngineFactory() : HttpClientEngineFactory<*>
