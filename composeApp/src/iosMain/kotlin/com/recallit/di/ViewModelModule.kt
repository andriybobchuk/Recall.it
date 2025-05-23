package com.recallit.di

import com.recallit.data.repository.DummyCoreRepositoryImpl
import com.recallit.domain.repository.CoreRepository
import com.recallit.presentation.cards_screen.CardsViewModel
import com.recallit.presentation.packs_screen.PacksViewModel
import org.koin.dsl.module

actual val viewModelModule = module {
    single { PacksViewModel(repository = get()) }
    factory { (packId: Int) -> CardsViewModel(packId, get()) }

    single<CoreRepository> { DummyCoreRepositoryImpl() }
}