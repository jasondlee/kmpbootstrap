@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
package com.steeplesoft.kmp.bootstrap

import androidx.room.RoomDatabase
import com.steeplesoft.kmp.bootstrap.room.AppDatabase
import io.ktor.client.engine.HttpClientEngineFactory

expect object AppContext

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

expect object AppLogger {
    fun e(tag: String, message: String, throwable: Throwable? = null)
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
}

expect fun getClientEngineFactory() : HttpClientEngineFactory<*>
