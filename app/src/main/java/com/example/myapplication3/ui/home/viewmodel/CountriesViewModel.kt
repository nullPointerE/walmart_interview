package com.example.myapplication3.ui.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication3.data.model.Country
import com.example.myapplication3.data.repository.CountriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val repository: CountriesRepository,
) : ViewModel() {
    companion object {
        const val COUNTRY_KEY = "countries"
    }

    private val _countries = MutableLiveData<List<Country>>(emptyList())
    val countries: LiveData<List<Country>> = _countries

    fun fetchCountries(forceFetch: Boolean = false) {
        if (forceFetch || _countries.value.isNullOrEmpty())
            // add loading signal
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val countryList = repository.fetchCountries()
                    _countries.postValue(countryList)
                } catch (e: Exception) {
                    // Handle error message
                    _countries.postValue(emptyList())
                }
            }
    }
}