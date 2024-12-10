package com.steeplesoft.kmp.bootstrap

import android.app.Application
import com.steeplesoft.kmp.bootstrap.context.AppContext

class App : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    override fun onCreate() {
        super.onCreate()

        AppContext.apply { set(applicationContext) }
    }
}
