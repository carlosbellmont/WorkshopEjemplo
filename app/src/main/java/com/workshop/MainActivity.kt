package com.workshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.workshop.ui.theme.WorkshopTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCharacters()
        lifecycleScope.launch {
            viewModel.uiState.collect {
                setContent {
                    DrawUi(state = it)
                }
            }
        }
    }

    @Composable
    fun DrawUi(state: MainActivityViewModel.State) {
        WorkshopTheme {
            Column(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
            ) {
                when(state) {
                    is MainActivityViewModel.State.Idle -> DrawLoading()
                    is MainActivityViewModel.State.Success -> DrawSuccess(state)
                    is MainActivityViewModel.State.Error -> DrawError()
                }
            }
        }
    }
}

@Composable
private fun DrawError() {
    TODO("Not yet implemented")
}

@Composable
private fun DrawSuccess(data: MainActivityViewModel.State.Success) {
    LazyColumn {
        item {
            Text(
                text = "Cabecera",
                fontSize = 16.sp,
                modifier = Modifier,
            )
        }
        items(data.characters){ character ->
            Text(
                text = character.name,
                fontSize = 12.sp,
                modifier = Modifier,
            )
            Text(
                text = character.height,
                fontSize = 12.sp,
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun DrawLoading() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.primary,
    )
}

@Preview(showBackground = true, device = "id:pixel_5")
@Composable
fun GreetingPreview() {
    WorkshopTheme {
        DrawLoading()
    }
}