package com.steeplesoft.kmp.bootstrap

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val client = HttpClient(getClientEngineFactory()) {
    defaultRequest {
        headers {
            appendMissing(HttpHeaders.Accept, listOf("application/json"))
        }
    }

    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
        sanitizeHeader { header -> header == HttpHeaders.Authorization }
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }) // Example: Register JSON content transformation
        // Add more transformations as needed for other content types
    }

    /*
    install(Auth) {
        // Configure authentication
        bearer {
            loadTokens {
                BearerTokens(
                    accessToken = "...",
                    refreshToken = null
                )
            }
        }
    }
    */
}
