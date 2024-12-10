package com.steeplesoft.kmp.bootstrap.components.userlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.kmp.bootstrap.LoadingScreen
import com.steeplesoft.kmp.bootstrap.Status

@Composable
fun userList(component: UserListComponent,
             modifier: Modifier
) {
    val status by component.requestStatus.subscribeAsState()

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("List") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (status) {
                Status.LOADING -> {
                    LoadingScreen(modifier)
                }
                Status.SUCCESS -> {
                    LazyColumn(
                        state = rememberLazyListState(),
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        items(component.results) { user ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp)
                            ) {
                                Text(user.firstName!!)
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    Text("Error loading results")
                }
            }
        }
    }
}
