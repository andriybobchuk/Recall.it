package com.recallit.di

import com.recallit.data.repository.DummyCoreRepositoryImpl
import com.recallit.domain.repository.CoreRepository
import com.recallit.presentation.cards_screen.CardsViewModel
import com.recallit.presentation.packs_screen.MainViewModel
import com.recallit.presentation.packs_screen.PacksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModel { PacksViewModel(repository = get()) }
    viewModel { CardsViewModel(repository = get()) }

    single<CoreRepository> { DummyCoreRepositoryImpl() }
}