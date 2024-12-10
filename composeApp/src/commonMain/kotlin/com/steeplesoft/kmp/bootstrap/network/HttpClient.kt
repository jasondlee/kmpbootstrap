package com.steeplesoft.kmp.bootstrap.network

import androidx.datastore.preferences.core.stringPreferencesKey
import com.steeplesoft.kmp.bootstrap.TAG
import com.steeplesoft.kmp.bootstrap.context.AppContext
import com.steeplesoft.kmp.bootstrap.logger.AppLogger
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.serialization.json.Json
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.first


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
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
    install(Auth) {
        // Configure authentication
        bearer {
            loadTokens {
                BearerTokens(
                    accessToken = AppContext.dataStore.data.map { preferences ->
                        preferences[PreferencesKeys.ACCESS_TOKEN] ?: ""
                    }.first(),
                    refreshToken = null
                )
            }
        }
    }
}

object PreferencesKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
}
