package com.communisol.clappybee

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.communisol.clappybee.di.initializeKoin

fun main() = application {
    initializeKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "ClappyBee",
    ) {
        App()
    }
}