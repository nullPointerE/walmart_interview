import com.example.myapplication3.data.model.Country
import com.example.myapplication3.data.model.Currency
import com.example.myapplication3.data.model.Language
import com.example.myapplication3.data.repository.CountriesRepository
import com.example.myapplication3.network.CountriesApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CountriesRepositoryTest {

    private lateinit var apiService: CountriesApiService
    private lateinit var repository: CountriesRepository

    @Before
    fun setUp() {
        apiService = mock()
        repository = CountriesRepository(apiService)
    }

    @Test
    fun `fetchCountries returns a list of countries`() = runTest {
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
            ),
            Country(
                name = "Åland Islands",
                region = "Europe",
                code = "AX",
                capital = "Mariehamn",
                currency = Currency("EUR", "Euro", "€"),
                flag = "https://restcountries.eu/data/ala.svg",
                language = Language("sv", "Swedish")
            )
        )

        whenever(apiService.getCountries()).thenReturn(mockCountries)

        // Act
        val result = repository.fetchCountries()

        // Assert
        assertEquals(mockCountries, result)
    }

    @Test(expected = Exception::class)
    fun `fetchCountries throws an exception when API fails`() = runTest {
        // Arrange
        whenever(apiService.getCountries()).thenThrow(RuntimeException("API Error"))

        // Act
        repository.fetchCountries()
    }
}
