package com.github.abuballan.coilchucker.internal.ui.navigation

import com.github.abuballan.coilchucker.R

enum class TopLevelDestination(
    val titleTextId: Int,
) {
    TRANSACTIONS(
        titleTextId = R.string.transactions_tab
    ),
    MEMORY_CACHE(
        titleTextId = R.string.memory_cache_tab
    ),
    DISK_CACHE(
        titleTextId = R.string.disk_cache_tab
    )
}