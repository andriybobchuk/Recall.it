package com.recallit.di

import com.recallit.presentation.packs_screen.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::MainViewModel)
}