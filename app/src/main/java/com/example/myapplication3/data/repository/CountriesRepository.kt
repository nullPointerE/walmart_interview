package com.example.myapplication3.data.repository

import com.example.myapplication3.data.model.Country
import com.example.myapplication3.network.CountriesApiService

class CountriesRepository(private val apiService: CountriesApiService) {
    suspend fun fetchCountries(): List<Country> {
        return apiService.getCountries()
    }
}