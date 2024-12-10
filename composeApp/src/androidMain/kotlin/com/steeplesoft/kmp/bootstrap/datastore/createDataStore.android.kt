package com.steeplesoft.kmp.bootstrap.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.steeplesoft.kmp.bootstrap.context.AppContext

actual fun createPlatformDataStore(): DataStore<Preferences> =
    createDataStore(
        producePath = { AppContext.get().filesDir.resolve(dataStoreFileName).absolutePath }
    )
