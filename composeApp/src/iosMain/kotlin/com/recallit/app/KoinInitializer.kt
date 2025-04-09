package com.recallit.app

import com.recallit.di.appModule
import org.koin.core.context.startKoin
import com.recallit.di.viewModelModule

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(appModule, viewModelModule)
        }
    }
}