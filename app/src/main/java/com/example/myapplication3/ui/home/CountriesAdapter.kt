package com.example.myapplication3.ui.home

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication3.R
import com.example.myapplication3.data.model.Country


class CountriesAdapter :
    ListAdapter<Country, CountriesAdapter.CountryViewHolder>(CountryDiffCallback()) {

    class CountryViewHolder(val view: android.view.View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: android.view.ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = getItem(position)
        val nameTextView = holder.view.findViewById<android.widget.TextView>(R.id.country_name)
        val capitalTextView =
            holder.view.findViewById<android.widget.TextView>(R.id.country_capital)
        val codeTextView = holder.view.findViewById<android.widget.TextView>(R.id.country_code)

        nameTextView.text = "${country.name}, ${country.region}"
        codeTextView.text = "${country.language.code}"
        capitalTextView.text = "${country.capital}"
    }

}

class CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
    override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem.code == newItem.code
    }

    override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
        return oldItem == newItem
    }
}
