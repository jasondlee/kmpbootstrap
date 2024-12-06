package com.steeplesoft.kmp.bootstrap.userlist

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.steeplesoft.kmp.bootstrap.Status
import com.steeplesoft.kmp.bootstrap.room.User
import com.steeplesoft.kmp.bootstrap.room.db
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
    }
}
