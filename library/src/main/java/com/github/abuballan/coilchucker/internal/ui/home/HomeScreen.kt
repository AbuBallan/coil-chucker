package com.github.abuballan.coilchucker.internal.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.github.abuballan.coilchucker.internal.ui.disk.DiskCacheScreen
import com.github.abuballan.coilchucker.internal.ui.disk.DiskCacheUiState
import com.github.abuballan.coilchucker.internal.ui.memory.MemoryCacheScreen
import com.github.abuballan.coilchucker.internal.ui.memory.MemoryCacheUiState
import com.github.abuballan.coilchucker.internal.ui.navigation.TopLevelDestination
import com.github.abuballan.coilchucker.internal.ui.requests.TransactionsScreen
import com.github.abuballan.coilchucker.internal.ui.requests.TransactionsUiState
import com.github.abuballan.coilchucker.internal.ui.theme.CoilChuckerTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    memoryCacheUiState: MemoryCacheUiState?,
    diskCacheUiState: DiskCacheUiState?,
    transactionsUiState: TransactionsUiState?
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HomeTopBar()
        }
    ) { contentPadding ->
        HomeContent(
            modifier = Modifier.padding(contentPadding),
            memoryCacheUiState = memoryCacheUiState,
            diskCacheUiState = diskCacheUiState,
            transactionsUiState = transactionsUiState
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeContent(
    modifier: Modifier = Modifier,
    memoryCacheUiState: MemoryCacheUiState?,
    diskCacheUiState: DiskCacheUiState?,
    transactionsUiState: TransactionsUiState?
) {
    Column(modifier = modifier.fillMaxSize()) {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        val tabs = TopLevelDestination.values()
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, topLevelDestination ->
                Tab(
                    text = {
                        Text(text = stringResource(id = topLevelDestination.titleTextId))
                    },
                    selected = index == pagerState.currentPage,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            pageCount = tabs.size,
            state = pagerState
        ) { page ->
            when (tabs[page]) {
                TopLevelDestination.TRANSACTIONS -> {
                    TransactionsScreen(transactionsUiState)
                }

                TopLevelDestination.MEMORY_CACHE -> {
                    MemoryCacheScreen(memoryCacheUiState = memoryCacheUiState)
                }

                TopLevelDestination.DISK_CACHE -> {
                    DiskCacheScreen(diskCacheUiState = diskCacheUiState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar() {
    TopAppBar(
        title = {
            Text(text = "Coil Chucker")
        },
    )
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    CoilChuckerTheme {
        HomeScreen(
            memoryCacheUiState = MemoryCacheUiState(
                memorySize = 50,
                memoryMaxSize = 100,
                memoryCacheImageList = listOf()
            ),
            diskCacheUiState = DiskCacheUiState(
                diskSize = 50,
                diskMaxSize = 50,
                diskCacheImageList = listOf()
            ),
            transactionsUiState = TransactionsUiState(listOf())
        )
    }
}