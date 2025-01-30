package com.communisol.clappybee

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform