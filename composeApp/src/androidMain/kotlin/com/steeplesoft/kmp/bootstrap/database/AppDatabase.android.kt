package com.steeplesoft.kmp.bootstrap.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.steeplesoft.kmp.bootstrap.context.AppContext

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context = AppContext.get()
    return Room.databaseBuilder<AppDatabase>(context,
        context.getDatabasePath(dbFileName).absolutePath)
}
