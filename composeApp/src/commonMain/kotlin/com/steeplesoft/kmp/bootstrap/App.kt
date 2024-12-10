package com.steeplesoft.kmp.bootstrap

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.steeplesoft.kmp.bootstrap.components.root.RootComponent
import com.steeplesoft.kmp.bootstrap.components.root.RootContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        RootContent(rootComponent)
    }
}
