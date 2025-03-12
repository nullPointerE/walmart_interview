package com.example.myapplication3.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.R
import com.example.myapplication3.data.repository.CountriesRepository
import com.example.myapplication3.network.CountriesApiService
import com.example.myapplication3.ui.home.viewmodel.CountriesViewModel
import com.example.myapplication3.ui.home.viewmodel.CountriesViewModelFactory
import com.example.myapplication3.utiles.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.activity.viewModels

class MainActivity(private val viewModelFactory: CountriesViewModelFactory? = null) : AppCompatActivity() {
    private val viewModel: CountriesViewModel by viewModels {
        viewModelFactory ?: let {
            val apiService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CountriesApiService::class.java)
            val repository = CountriesRepository(apiService)
            CountriesViewModelFactory(repository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)

        val recyclerView = findViewById<RecyclerView>(R.id.country_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CountriesAdapter()
        recyclerView.adapter = adapter
        recyclerView.setItemViewCacheSize(10)
        viewModel.countries.observe(this) { countries ->
            adapter.submitList(countries)
        }

        viewModel.fetchCountries()
    }
}

