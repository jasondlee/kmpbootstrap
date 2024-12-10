package com.steeplesoft.kmp.bootstrap.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.steeplesoft.kmp.bootstrap.logger.AppLogger
import com.steeplesoft.kmp.bootstrap.TAG
import io.ktor.utils.io.core.String
import kmpbootstrap.composeapp.generated.resources.Res
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

internal const val dbFileName = "kmp-demo.db"
expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>


val db = getRoomDatabase()


fun getRoomDatabase(): AppDatabase {
    val database = getDatabaseBuilder()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .addCallback(PrepopulateRoomCallback())
        .build()
    // Access the DB to force the onCreate() to happen early. Is there a better way?
    database.userDao()
    return database
}

@OptIn(ExperimentalResourceApi::class)
suspend fun readFile(path: String): String {
    val readBytes = Res.readBytes(path)
    return String(readBytes)
}

class PrepopulateRoomCallback() : RoomDatabase.Callback() {

    override fun onCreate(connection: SQLiteConnection) {
        super.onCreate(connection)

        CoroutineScope(Dispatchers.IO).launch {
            prePopulateUsers()
        }
    }

    private suspend fun prePopulateUsers() {
        try {
            val file = readFile("files/dummyData.json")
            val userList = Json.decodeFromString<List<User>>(file)
            userList.takeIf { it.isNotEmpty() }?.let { list ->
                val userDao = db.userDao()
                list.forEach {
                    userDao.insertAll(it)
                }
                AppLogger.d(TAG, "successfully pre-populated users into database")
            }
        } catch (exception: Exception) {
            AppLogger.e(TAG, exception.message ?: "failed to pre-populate users into database")
        }
    }
}

@Database(entities = [User::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
