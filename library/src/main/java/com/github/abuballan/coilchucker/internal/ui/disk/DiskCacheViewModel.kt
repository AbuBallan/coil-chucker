package com.github.abuballan.coilchucker.internal.ui.disk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.github.abuballan.coilchucker.internal.support.CoilDiskCacheHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DiskCacheViewModel(
    private val diskCacheHelper: CoilDiskCacheHelper
) : ViewModel() {

    private val _diskState: MutableStateFlow<DiskCacheUiState?> = MutableStateFlow(null)
    val diskState: StateFlow<DiskCacheUiState?> = _diskState.asStateFlow()

    init {
        viewModelScope.launch {
            _diskState.emit(
                DiskCacheUiState(
                    diskSize = diskCacheHelper.getDiskCacheSize(),
                    diskMaxSize = diskCacheHelper.getDiskCacheMaxSize(),
                    diskCacheImageList = diskCacheHelper.getDiskCacheImageList()
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
                val application = checkNotNull(extras[APPLICATION_KEY])
                return DiskCacheViewModel(
                    CoilDiskCacheHelper(application),
                ) as T
            }
        }
    }
}