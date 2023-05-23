package com.github.abuballan.coilchucker.internal.ui.memory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.github.abuballan.coilchucker.internal.support.CoilMemoryCacheHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MemoryCacheViewModel(
    private val coilMemoryCacheHelper: CoilMemoryCacheHelper
) : ViewModel() {

    private val _memoryState: MutableStateFlow<MemoryCacheUiState?> = MutableStateFlow(null)
    val memoryState: StateFlow<MemoryCacheUiState?> = _memoryState.asStateFlow()

    init {
        viewModelScope.launch {
            _memoryState.emit(
                MemoryCacheUiState(
                    memorySize = coilMemoryCacheHelper.getMemoryCacheSize(),
                    memoryMaxSize = coilMemoryCacheHelper.getMemoryCacheMaxSize(),
                    memoryCacheImageList = coilMemoryCacheHelper.getMemoryCacheImageList()
                )
            )
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return MemoryCacheViewModel(
                    CoilMemoryCacheHelper(application),
                ) as T
            }
        }
    }
}