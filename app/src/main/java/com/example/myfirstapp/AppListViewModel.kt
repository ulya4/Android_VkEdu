package com.example.myfirstapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class AppListViewModel : ViewModel() {
    val apps: List<AppInfo> = loadApps()

    private val snackbarEvents = Channel<Unit>(Channel.BUFFERED)
    val snackbarMessages = snackbarEvents.receiveAsFlow()

    fun onToolbarClick() {
        snackbarEvents.trySend(Unit)
    }
}
