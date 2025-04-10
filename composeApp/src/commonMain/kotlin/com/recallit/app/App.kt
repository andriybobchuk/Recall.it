package com.recallit.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.recallit.navigation.NavigationHost
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            NavigationHost()
        }
    }
}

@Composable
inline fun <reified T: ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}

@Composable
inline fun <reified T : ViewModel> koinViewModel(vararg params: Any): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T> { parametersOf(*params) }
    }
}
