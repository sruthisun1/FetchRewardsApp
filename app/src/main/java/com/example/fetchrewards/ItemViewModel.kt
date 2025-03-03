package com.example.fetchrewards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchrewards.data.Item
import com.example.fetchrewards.data.ItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel responsible for managing UI state and fetching data
// Uses Kotlin Coroutines with StateFlow for reactive UI updates
class ItemViewModel(private val repository: ItemRepository = ItemRepository()) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadItems()
    }

    // Loads items from the repository, updates UI state accordingly
    // Handles errors gracefully and ensures proper UI updates
    fun loadItems() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                val items = repository.getItems()
                val groupedItems = repository.getItemsGroupedByListId(items)
                _uiState.value = UiState.Success(groupedItems)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    // Represents different UI states: Loading, Success, and Error
    sealed class UiState {
        object Loading : UiState()
        data class Success(val groupedItems: Map<Int, List<Item>>) : UiState()
        data class Error(val message: String) : UiState()
    }
}