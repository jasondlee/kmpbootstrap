package com.steeplesoft.kmp.bootstrap.clickme

import com.arkivanov.decompose.ComponentContext

interface ClickMeComponent {
    fun onUserListClicked()
}


class ClickMeComponentImpl(componentContext: ComponentContext,
    val callback : () -> Unit) : ClickMeComponent,
    ComponentContext by componentContext {

    override fun onUserListClicked() {
        callback()
    }
}
