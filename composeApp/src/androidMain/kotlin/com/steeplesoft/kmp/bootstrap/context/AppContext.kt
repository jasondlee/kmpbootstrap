package com.steeplesoft.kmp.bootstrap.context

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.steeplesoft.kmp.bootstrap.datastore.dataStore
import java.lang.ref.WeakReference

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object AppContext {
    private lateinit var _dataStore : DataStore<Preferences>
    private var value: WeakReference<Context?>? = null
    fun set(context: Context) {
        value = WeakReference(context)
        _dataStore = dataStore(context)
    }
    internal fun get(): Context {
        return value?.get() ?: throw RuntimeException("Context Error")
    }

    actual val dataStore: DataStore<Preferences>
        get() = _dataStore
}
