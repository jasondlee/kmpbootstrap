package com.steeplesoft.kmp.bootstrap.components.userlist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.steeplesoft.kmp.bootstrap.Status
import com.steeplesoft.kmp.bootstrap.database.User
import com.steeplesoft.kmp.bootstrap.database.db
import com.steeplesoft.kmp.bootstrap.network.client
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface UserListComponent {
    var results: List<User>
    var requestStatus : MutableValue<Status>
}

class UserListComponentImpl(componentContext: ComponentContext) : UserListComponent,
    ComponentContext by componentContext {
    override var requestStatus  = MutableValue(Status.LOADING)
    override var results: List<User> = listOf()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val userDao = db.userDao()

            // Brief delay just to show off the Status support
            delay(1000)
            results = userDao.getAll()
            requestStatus.update { Status.SUCCESS }
        }
        CoroutineScope(Dispatchers.IO).launch {
            NetworkRepository().search()
        }
    }
}

class NetworkRepository {
    suspend fun search(): List<String> {
        val response: HttpResponse = client.request("https://microsoftedge.github.io/Demos/json-dummy-data/64KB.json")

        val body = response.body<String>()
        println(body)
        return listOf()
    }
}
