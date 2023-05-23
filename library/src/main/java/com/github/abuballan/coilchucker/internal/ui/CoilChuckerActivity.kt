package com.github.abuballan.coilchucker.internal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.abuballan.coilchucker.internal.ui.disk.DiskCacheViewModel
import com.github.abuballan.coilchucker.internal.ui.home.HomeScreen
import com.github.abuballan.coilchucker.internal.ui.memory.MemoryCacheViewModel
import com.github.abuballan.coilchucker.internal.ui.requests.TransactionsViewModel
import com.github.abuballan.coilchucker.internal.ui.theme.CoilChuckerTheme

class CoilChuckerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoilChuckerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val memoryCacheViewModel: MemoryCacheViewModel =
                        viewModel(factory = MemoryCacheViewModel.Factory)
                    val memoryCacheUiState by memoryCacheViewModel.memoryState.collectAsStateWithLifecycle()

                    val diskCacheViewModel: DiskCacheViewModel =
                        viewModel(factory = DiskCacheViewModel.Factory)
                    val diskCacheUiState by diskCacheViewModel.diskState.collectAsStateWithLifecycle()

                    val transactionsViewModel: TransactionsViewModel = viewModel()
                    val transactionsUiState by transactionsViewModel.transactionsState.collectAsStateWithLifecycle()

                    HomeScreen(
                        memoryCacheUiState,
                        diskCacheUiState,
                        transactionsUiState
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isInForeground = true
    }

    override fun onPause() {
        super.onPause()
        isInForeground = false
    }

    companion object {

        var isInForeground: Boolean = false
            private set
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoilChuckerTheme {

    }
}