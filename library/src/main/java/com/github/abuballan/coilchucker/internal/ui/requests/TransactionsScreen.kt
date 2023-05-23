package com.github.abuballan.coilchucker.internal.ui.requests

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun TransactionsScreen(transactionsUiState: TransactionsUiState?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(transactionsUiState?.transactions ?: listOf()) { transaction ->
            TransactionItem(transaction = transaction)
        }
    }
}