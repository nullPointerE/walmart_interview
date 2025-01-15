package com.example.myapplication3
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.test.core.app.ApplicationProvider
import com.example.myapplication3.data.model.Country
import com.example.myapplication3.data.model.Currency
import com.example.myapplication3.data.model.Language
import com.example.myapplication3.ui.home.CountriesAdapter
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CountriesAdapterTest {

    private lateinit var adapter: CountriesAdapter
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val mockCountries = listOf(
        Country(
            name = "Afghanistan",
            region = "Asia",
            code = "AF",
            capital = "Kabul",
            currency = Currency(code = "AFN", name = "Afghan afghani", symbol = "؋"),
            flag = "https://restcountries.eu/data/afg.svg",
            language = Language("ps", "Pashto")
        )
    )

    @Before
    fun setUp() {
        adapter = CountriesAdapter()
        adapter.submitList(mockCountries)
    }

    @Test
    fun getItemCount_returns_correct_size() {
        assertEquals(mockCountries.size, adapter.itemCount)
    }

    @Test
    fun onBindViewHolder_binds_data_correctly() {
        val parent = LayoutInflater.from(context)
            .inflate(R.layout.item_country, null, false) as ViewGroup
        val viewHolder = adapter.onCreateViewHolder(parent, 0)

        adapter.onBindViewHolder(viewHolder, 0)

        val country = mockCountries[0]
        val nameTextView = viewHolder.itemView.findViewById<TextView>(R.id.country_name)
        val capitalTextView = viewHolder.itemView.findViewById<TextView>(R.id.country_capital)
        val codeTextView = viewHolder.itemView.findViewById<TextView>(R.id.country_code)

        assertEquals("${country.name}, ${country.region}", nameTextView.text)
        assertEquals(country.capital, capitalTextView.text)
        assertEquals(country.language.code, codeTextView.text)
    }
}
