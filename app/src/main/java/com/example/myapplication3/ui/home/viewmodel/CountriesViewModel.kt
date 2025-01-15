package com.example.myapplication3.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication3.data.model.Country
import com.example.myapplication3.data.repository.CountriesRepository
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val repository: CountriesRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        const val COUNTRY_KEY = "countries"
    }

    private val _countries = savedStateHandle.getLiveData(COUNTRY_KEY, emptyList<Country>())
    val countries: LiveData<List<Country>> get() = _countries

    fun fetchCountries(forceFetch: Boolean = false) {
        if (forceFetch || _countries.value.isNullOrEmpty())
            viewModelScope.launch {
                try {
                    val countryList = repository.fetchCountries()
                    _countries.value = countryList
                    savedStateHandle[COUNTRY_KEY] = countryList
                } catch (e: Exception) {
                    // Handle error
                    _countries.value = emptyList()
                }
            }
    }
}