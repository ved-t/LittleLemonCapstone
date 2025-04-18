package com.example.littlelemoncapstone

import android.graphics.pdf.models.ListItem
import android.util.Log
import android.view.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.example.littlelemoncapstone.ui.theme.LittleLemonBlack
import com.example.littlelemoncapstone.ui.theme.LittleLemonWHite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(private val repository: MenuRepository): ViewModel() {
    private var data by mutableStateOf<MenuResponse?>(null)
    var isLoading by mutableStateOf(false)

    private val _menuItems = repository.getMenu().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(1000),
        emptyList()
    )
    private val menuItems: StateFlow<List<MenuItemEntity>> = _menuItems

    private val _category = MutableStateFlow("")
    val category : StateFlow<String> = _category

    val categoryFilter: StateFlow<List<MenuItemEntity>> = combine(menuItems, category){item, query ->
        item.filter { it.category.contains(query, ignoreCase = true) }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun onCategoryChange(category: String){
        _category.value = category
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery : StateFlow<String> = _searchQuery

    val filterOnCategory: StateFlow<List<MenuItemEntity>> = combine(categoryFilter, searchQuery){item, query ->
        item.filter { it.title.contains(query, ignoreCase = true) }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun onQueryChange(searchQuery: String){
        _searchQuery.value = searchQuery
    }

    private val _categoryList = MutableStateFlow(listOf("starters", "mains", "desserts", "drinks"))
    val categoryList : StateFlow<List<String>> = _categoryList

    init {
        getData()
    }

    private fun getData(){
        viewModelScope.launch {
            isLoading = true
            try {
                if(repository.isEmpty()){
                    println("Database is empty")
                    data = repository.fetchMenu()
                    data?.let { repository.storeMenu(it.menu) }
                }
                else{
                    println("Database is not empty")
                }
            }
            catch (e: Exception){
                e.printStackTrace()
            }
            finally {
                isLoading = false
            }
        }
    }
}