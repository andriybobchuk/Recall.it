package com.recallit.di

import com.recallit.data.repository.DummyCoreRepositoryImpl
import com.recallit.domain.repository.CoreRepository
import com.recallit.presentation.cards_screen.CardsViewModel
import com.recallit.presentation.packs_screen.PacksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModel { PacksViewModel(repository = get()) }
    viewModel { (packId: Int) -> CardsViewModel(packId = packId, repository = get()) }

    single<CoreRepository> { DummyCoreRepositoryImpl() }
}