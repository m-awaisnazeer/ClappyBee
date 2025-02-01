package com.communisol.clappybee.domain

data class PipePair(
    var x: Float,
    val y: Float,
    val topHeight: Float,
    val bottomHeight: Float,
    val scored: Boolean = false
)
