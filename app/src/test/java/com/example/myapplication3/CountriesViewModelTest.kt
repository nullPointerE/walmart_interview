package com.example.myapplication3
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.myapplication3.data.model.Country
import com.example.myapplication3.data.model.Currency
import com.example.myapplication3.data.model.Language
import com.example.myapplication3.data.repository.CountriesRepository
import com.example.myapplication3.ui.home.viewmodel.CountriesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CountriesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: CountriesRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: CountriesViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        // Set Main dispatcher for tests
        Dispatchers.setMain(testDispatcher)

        repository = mock()
        savedStateHandle = SavedStateHandle()
        viewModel = CountriesViewModel(repository, savedStateHandle)
    }

    @After
    fun tearDown() {
        // Reset Main dispatcher after tests
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCountries fetches data and updates LiveData`() = runTest {
        // Arrange
        val mockCountries = listOf(
            Country(
                name = "Afghanistan",
                region = "Asia",
                code = "AF",
                capital = "Kabul",
                currency = Currency("AFN", "Afghan afghani", "؋"),
                flag = "https://restcountries.eu/data/afg.svg",
                language = Language("ps", "Pashto")
            )
        )
        whenever(repository.fetchCountries()).thenReturn(mockCountries)

        // Act
        viewModel.fetchCountries(forceFetch = true)

        // Assert
        assertEquals(mockCountries, viewModel.countries.value)
        assertEquals(mockCountries, savedStateHandle[CountriesViewModel.COUNTRY_KEY])
    }

    @Test
    fun `fetchCountries handles exception and sets empty list`() = runTest {
        // Arrange
        whenever(repository.fetchCountries()).thenThrow(RuntimeException("API Error"))

        // Act
        viewModel.fetchCountries(forceFetch = true)

        // Assert
        assertEquals(emptyList<Country>(), viewModel.countries.value)
        assertEquals(emptyList<Country>(), savedStateHandle[CountriesViewModel.COUNTRY_KEY])
    }

    @Test
    fun `fetchCountries does not fetch if data is already loaded`() = runTest {
        // Arrange
        val existingCountries = listOf(
            Country(
                name = "Afghanistan",
                region = "Asia",
                code = "AF",
                capital = "Kabul",
                currency = Currency("AFN", "Afghan afghani", "؋"),
                flag = "https://restcountries.eu/data/afg.svg",
                language = Language("ps", "Pashto")
            )
        )
        savedStateHandle[CountriesViewModel.COUNTRY_KEY] = existingCountries

        // Act
        viewModel.fetchCountries(forceFetch = false)

        // Assert
        verify(repository, never()).fetchCountries()
        assertEquals(existingCountries, viewModel.countries.value)
    }
}
