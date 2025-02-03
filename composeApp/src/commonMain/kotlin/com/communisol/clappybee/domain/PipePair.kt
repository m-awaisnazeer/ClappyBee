package com.communisol.clappybee.domain

import androidx.compose.ui.unit.IntOffset

data class PipePair(
    var x: Float,
    val y: Float,
    val topHeight: Float,
    val bottomHeight: Float,
    var scored: Boolean = false
)

