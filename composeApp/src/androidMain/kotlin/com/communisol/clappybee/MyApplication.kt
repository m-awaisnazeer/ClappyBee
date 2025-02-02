package com.communisol.clappybee

import android.app.Application
import com.communisol.clappybee.di.initializeKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }
}