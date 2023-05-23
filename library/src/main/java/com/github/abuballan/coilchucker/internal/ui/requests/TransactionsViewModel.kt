package com.github.abuballan.coilchucker.internal.ui.requests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abuballan.coilchucker.internal.data.repository.RepositoryProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionsViewModel : ViewModel() {

    private val _transactionsState: MutableStateFlow<TransactionsUiState?> = MutableStateFlow(null)
    internal val transactionsState: StateFlow<TransactionsUiState?> =
        _transactionsState.asStateFlow()

    init {
        viewModelScope.launch {
            _transactionsState.emit(
                TransactionsUiState(
                    transactions = RepositoryProvider.transaction().getAllTransactions()
                )
            )
        }
    }
}