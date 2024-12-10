package com.steeplesoft.kmp.bootstrap.context

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect object AppContext {
    val dataStore : DataStore<Preferences>
}
