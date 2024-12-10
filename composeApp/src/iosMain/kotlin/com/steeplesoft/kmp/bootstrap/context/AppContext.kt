package com.steeplesoft.kmp.bootstrap.context

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.steeplesoft.kmp.bootstrap.datastore.dataStore

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object AppContext {
    actual val dataStore: DataStore<Preferences> = dataStore()
}
