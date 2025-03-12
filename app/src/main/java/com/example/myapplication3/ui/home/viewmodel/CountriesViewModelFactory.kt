package com.example.myapplication3.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication3.data.repository.CountriesRepository
import org.jetbrains.annotations.VisibleForTesting

class CountriesViewModelFactory(
    private val repository: CountriesRepository,
) :
    ViewModelProvider.Factory {

    @VisibleForTesting
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(CountriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CountriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}