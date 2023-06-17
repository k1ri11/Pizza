package com.company.pizza

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.company.pizza.domain.repository.Repository
import com.company.pizza.presentation.HomeViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val repository: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}