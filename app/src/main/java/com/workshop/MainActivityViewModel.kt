package com.workshop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.random.Random


class MainActivityViewModel: ViewModel() {
    
    private val client = OkHttpClient()
    private val url = "https://swapi.dev/api/people"

    private val _uiState = MutableStateFlow<State>(State.Idle)
    val uiState : StateFlow<State> = _uiState.asStateFlow()

    sealed interface State {
        data object Idle : State
        data class Success(val characters: List<Character>) : State
        data class Error(val error: String) : State
    }

    fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = try {
                val request = Request.Builder()
                    .url(url)
                    .build()
                val response = client.newCall(request).execute()
                val responseInString = response.body?.string()
                responseInString?.let {
                    val hero = Json.decodeFromString<Hero>(responseInString)
                    val list = hero.results.map { Character(it.name, it.height) }
                    State.Success(list)
                }?: State.Error("Respuesta vacia")
            } catch (exception: IOException) {
                State.Error("Algo ha ido mal")
            }
        }
    }
}