package com.recallit.di

import com.recallit.data.repository.DummyCoreRepositoryImpl
import com.recallit.domain.repository.CoreRepository
import com.recallit.presentation.cards_screen.CardsViewModel
import com.recallit.presentation.packs_screen.MainViewModel
import com.recallit.presentation.packs_screen.PacksViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::MainViewModel)
    single { PacksViewModel(repository = get()) }
    single { CardsViewModel(repository = get()) }

    single<CoreRepository> { DummyCoreRepositoryImpl() }
}