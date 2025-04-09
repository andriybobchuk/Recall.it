package com.plcoding.jetpackviewmodel_cmp

import com.recallit.app.KoinInitializer
import android.app.Application

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}