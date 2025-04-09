package com.recallit.di

import org.koin.dsl.module

val appModule = module {
    single { "Hello world!" }
}