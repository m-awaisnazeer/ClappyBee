package com.communisol.clappybee

import androidx.compose.ui.window.ComposeUIViewController
import com.communisol.clappybee.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }