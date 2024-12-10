package com.steeplesoft.kmp.bootstrap.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal const val dataStoreFileName = "kmpbootstrap.preferences_pb"
fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

expect fun createPlatformDataStore() : DataStore<Preferences>

val dataStore: DataStore<Preferences> by lazy { createPlatformDataStore() }
