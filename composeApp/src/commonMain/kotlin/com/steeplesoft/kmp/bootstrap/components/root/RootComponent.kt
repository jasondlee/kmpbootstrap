package com.steeplesoft.kmp.bootstrap.components.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.steeplesoft.kmp.bootstrap.components.clickme.ClickMeComponent
import com.steeplesoft.kmp.bootstrap.components.clickme.ClickMeComponentImpl
import com.steeplesoft.kmp.bootstrap.database.db
import com.steeplesoft.kmp.bootstrap.components.userlist.UserListComponent
import com.steeplesoft.kmp.bootstrap.components.userlist.UserListComponentImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed interface Child {
        class ClickMe(val component: ClickMeComponent) : Child
        class UserList(val component : UserListComponent) : Child
    }
}

@OptIn(DelicateDecomposeApi::class)
class RootComponentImpl(componentContext: ComponentContext) :
    RootComponent, ComponentContext by componentContext {
    private val nav = StackNavigation<NavigationConfig>()

    init {
        // This is super ugly, but it forces the DB to be created early, which works around
        // the issue where the first request to the DB is executed while the data is being
        // loaded. That results in a failed/empty request, for example. With the this, the
        // database is populated when the app starts. A better solution to this would probably
        // be LiveData, but I haven't been able to get that work just yet, so this hack will
        // suffice for now.
        CoroutineScope(Dispatchers.IO).launch {
            db.userDao().getAll()
        }
    }

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = nav,
        serializer = NavigationConfig.serializer(),
        initialConfiguration = NavigationConfig.ClickMe,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: NavigationConfig,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            is NavigationConfig.ClickMe ->
                RootComponent.Child.ClickMe(ClickMeComponentImpl(componentContext) {
                    nav.push(NavigationConfig.UserList)
                })
            is NavigationConfig.UserList ->
                RootComponent.Child.UserList(UserListComponentImpl(componentContext))
        }
    }
}

@Serializable
sealed interface NavigationConfig {
    @Serializable
    data object ClickMe : NavigationConfig
    @Serializable
    data object UserList : NavigationConfig
}
