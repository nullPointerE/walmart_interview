package com.example.myapplication3.ui.home.viewmodel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.myapplication3.data.repository.CountriesRepository
import org.jetbrains.annotations.VisibleForTesting

class CountriesViewModelFactory(
    private val repository: CountriesRepository,
    private val savedStateRegistryOwner: SavedStateRegistryOwner,
) :
    AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {

    @VisibleForTesting
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        if (modelClass.isAssignableFrom(CountriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CountriesViewModel(repository, handle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}