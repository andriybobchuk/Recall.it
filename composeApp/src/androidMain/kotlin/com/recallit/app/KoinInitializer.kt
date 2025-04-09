package com.recallit.app

import android.content.Context
import com.recallit.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import com.recallit.di.viewModelModule

actual class KoinInitializer(
    private val context: Context
) {
    actual fun init() {
        startKoin {
            androidContext(context)
            androidLogger()
            modules(appModule, viewModelModule)
        }
    }
}