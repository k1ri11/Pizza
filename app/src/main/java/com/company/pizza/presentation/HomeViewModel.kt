package com.company.pizza.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.pizza.data.models.MenuModel
import com.company.pizza.domain.repository.Repository
import com.company.pizza.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val menu: LiveData<Resource<MenuModel>> = repository.menu

    init {
        getMenu()
    }

    fun getMenu() = viewModelScope.launch(Dispatchers.IO) {
        repository.getMenu()
    }

}